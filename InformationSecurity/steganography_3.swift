import Foundation

let russianToLatin: [String: String] = [
    "Е": "E",
    "е": "e",
    "Т": "T",
    "у": "y",
    "О": "O",
    "о": "o",
    "Р": "P",
    "р": "p",
    "А": "A",
    "а": "a",
    "H": "Н",
    "К": "K",
    "Х": "X",
    "х": "x",
    "С": "C",
    "с": "c",
    "В": "B",
    "М": "M"
]

let latinToRussian: [String: String] = [
    "E": "Е",
    "e": "е",
    "T": "Т",
    "y": "у",
    "O": "О",
    "o": "о",
    "P": "Р",
    "p": "р",
    "A": "А",
    "a": "а",
    "H": "Н",
    "K": "К",
    "X": "Х",
    "x": "х",
    "C": "С",
    "c": "с",
    "B": "В",
    "M": "М"
]

extension String {
    typealias Byte = UInt8
    var hexToBytes: [Byte] {
        var start = startIndex
        return stride(from: 0, to: count, by: 2).compactMap { _ in
            let end = index(after: start)
            defer { start = index(after: end) }
            return Byte(self[start...end], radix: 16)
        }
    }
    var hexToBinary: String {
        return hexToBytes.map {
            let binary = String($0, radix: 2)
            return repeatElement("0", count: 8 - binary.count) + binary
        }.joined()
    }
}
print("Enter the test, sir")
var enteredText = readLine()!
let file = "first.txt"
var binaryStream = String()
var lineArray = [String]()

//ENCODED
if let data = enteredText.data(using: .windowsCP1251) {
    let encoded = data.map { String(format: "%02hhX", $0) + "." }.joined()
    var bytes = encoded.components(separatedBy: ".")
    bytes.removeLast()
    for byte in bytes {
        binaryStream += byte.hexToBinary
    }
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    let allText = try String(contentsOf: dir.appendingPathComponent(file), encoding: .windowsCP1251)
    allText.enumerateLines { (line, _) in
        lineArray.append(line + "\n")
    }
}

var recordText = ""
print("bytes: \(binaryStream)")

for indexLine in lineArray.indices {
    for indexCharacter in lineArray[indexLine].indices {
        if binaryStream.count != 0 {
            let binaryChar = binaryStream[..<binaryStream.index(binaryStream.startIndex, offsetBy: 1)]
            binaryStream.remove(at: binaryStream.startIndex)

            if russianToLatin.keys.contains(String(lineArray[indexLine][indexCharacter])) {
                if let latinValue = russianToLatin[String(lineArray[indexLine][indexCharacter])] {
                    if String(binaryChar) != "0" {
                        recordText += latinValue
                    } else {
                        recordText += String(lineArray[indexLine][indexCharacter])
                    }
                }
            } else {
                recordText += String(lineArray[indexLine][indexCharacter])
                binaryStream.insert(Character(String(binaryChar)), at: binaryStream.startIndex)
            }
        } else {
            recordText += String(lineArray[indexLine][indexCharacter...])
            break
        }
    }
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    do {
        try recordText.write(to: dir.appendingPathComponent(file), atomically: false, encoding: .windowsCP1251)
    }
    catch { }
}
print("encoded file")

//DECODED
let lala = readLine()
lineArray.removeAll()
binaryStream = ""
var counterBytes = 0
var decodedArray = [UInt8]()

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    let allText = try String(contentsOf: dir.appendingPathComponent(file), encoding: .windowsCP1251)
    allText.enumerateLines { (line, _) in
        lineArray.append(line + "\n")
    }
}

//futureWrittenText.enumerateLines { (line, _) in
//    lineArray.append(line + "\n")
//}

recordText = ""

for indexLine in lineArray.indices {
    for indexCharacter in lineArray[indexLine].indices {
        //if latin - 1, elif rus - 0
        if latinToRussian.keys.contains(String(lineArray[indexLine][indexCharacter])) {
            if let russianValue = latinToRussian[String(lineArray[indexLine][indexCharacter])] {
                recordText += russianValue
                binaryStream += "1"
                counterBytes += 1
            }
        } else if russianToLatin.keys.contains(String(lineArray[indexLine][indexCharacter])) {
            recordText += String(lineArray[indexLine][indexCharacter])
            binaryStream += "0"
            counterBytes += 1
        } else {
            recordText += String(lineArray[indexLine][indexCharacter])
        }

        if counterBytes == 8 {
            binaryStream += " "
            counterBytes = 0
        }
    }
}

binaryStream = binaryStream
    .split(separator: " ")
    .compactMap {
        decodedArray.append(UInt8(Int($0, radix: 2)!))
        return " "
    }
    .joined(separator: " ")

var decodedText = String(bytes: decodedArray, encoding: .windowsCP1251)

print("decoded text: \(decodedText!)")

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    do {
        try recordText.write(to: dir.appendingPathComponent(file), atomically: false, encoding: .windowsCP1251)
    }
    catch { }
}
