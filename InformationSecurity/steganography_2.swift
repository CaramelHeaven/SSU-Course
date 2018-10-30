import Foundation

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
let file = "second.txt"
var binaryStream = String()
var lineArray = [String]()
var cacheLines = String()

//ENCODED
if let data = enteredText.data(using: .windowsCP1251) {
    let encoded = data.map { String(format: "%02hhX", $0) + "." }.joined()
    var bytes = encoded.components(separatedBy: ".")
    bytes.removeLast()
    for byte in bytes {
        binaryStream += byte.hexToBinary
        print("binary: \(byte.hexToBinary)")
    }
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    let allText = try String(contentsOf: dir.appendingPathComponent(file), encoding: .windowsCP1251)
    allText.enumerateLines { (line, _) in
        lineArray.append(line)
    }
    cacheLines = allText
}

for index in lineArray.indices {
    var writenLine = ""
    var k = 0
    let separatedLine = lineArray[index].split(separator: " ")
    for i in separatedLine.indices {
        if i != separatedLine.count - 1 {
            if binaryStream.count > 1 {
                let binaryChar = binaryStream[..<binaryStream.index(binaryStream.startIndex, offsetBy: 1)]
                binaryStream.remove(at: binaryStream.startIndex)
                if String(binaryChar) != "0" {
                    writenLine += separatedLine[i] + "  "
                } else {
                    writenLine += separatedLine[i] + " "
                }
            } else {
                let binaryChar = binaryStream
                if binaryChar != "0" {
                    writenLine += separatedLine[i] + "  "
                } else {
                    writenLine += separatedLine[i] + " "
                }
                var lastIndex = index
                lastIndex += 1
                while lastIndex != separatedLine.count {
                    if lastIndex == separatedLine.count - 1 {
                        writenLine += separatedLine[lastIndex]
                    } else {
                        writenLine += separatedLine[lastIndex] + " "
                    }
                    lastIndex += 1
                }
                k += 1
                break
            }
        } else {
            writenLine += separatedLine[i]
        }
    }
    lineArray[index] = writenLine
    if k == 1 {
        break
    }
}

var recordText = ""

for changedLine in lineArray {
    recordText += changedLine + "\n"
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    do {
        try recordText.write(to: dir.appendingPathComponent(file), atomically: false, encoding: .windowsCP1251)
    }
    catch { }
}
print("encoded file")

//DECODED
print("Press any key for decoded this file")
let key = readLine()
lineArray.removeAll()
var revivalBytes = ""
var decodedArray = [UInt8]()

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    let allText = try String(contentsOf: dir.appendingPathComponent(file), encoding: .windowsCP1251)
    allText.enumerateLines { (line, _) in
        lineArray.append(line)
    }
}

var counterByte = 0

for line in lineArray {
    var massive = [Character]()
    for index in line.indices {
        massive.append(line[index])
    }
    for i in massive.indices {
        var nextChar = i
        var lastChar = i
        nextChar += 1
        lastChar -= 1
        if counterByte == 8 {
            revivalBytes += " "
            counterByte = 0
        }
        if nextChar < massive.count {
            if massive[i] == " " && massive[nextChar] == " " {
                revivalBytes += "1"
                counterByte += 1
            } else if massive[i] == " " && massive[nextChar] != " " && massive[lastChar] != " " {
                revivalBytes += "0"
                counterByte += 1
            }
        }
    }
}

print("revivaL: \(revivalBytes)")

revivalBytes = revivalBytes
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
        try cacheLines.write(to: dir.appendingPathComponent(file), atomically: false, encoding: .windowsCP1251)
    }
    catch { }
}
