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
        print("binary: \(byte.hexToBinary)")
    }
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    let allText = try String(contentsOf: dir.appendingPathComponent(file), encoding: .windowsCP1251)
    allText.enumerateLines { (line, _) in
        lineArray.append(line)
    }
}

for line in lineArray.indices {
    if binaryStream.count > 1 {
        let binaryChar = binaryStream[..<binaryStream.index(binaryStream.startIndex, offsetBy: 1)]
        binaryStream.remove(at: binaryStream.startIndex)
        if String(binaryChar) != "0" {
            lineArray[line] += " "
        }
    } else {
        let binaryChar = binaryStream
        if binaryChar != "0" {
            lineArray[line] += " "
        }
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

for decodedLine in lineArray {
    if counterByte == 8 {
        revivalBytes += " "
        counterByte = 0
    }
    if String(decodedLine.last!) == " " {
        revivalBytes += "1"
    } else {
        revivalBytes += "0"
    }
    counterByte += 1
}

revivalBytes = revivalBytes
    .split(separator: " ")
    .compactMap {
        decodedArray.append(UInt8(Int($0, radix: 2)!))
        return " "
    }
    .joined(separator: " ")

var decodedText = String(bytes: decodedArray, encoding: .windowsCP1251)
print("decoded text: \(decodedText!)")

recordText = ""

for changedLine in lineArray {
    recordText += changedLine.replacingOccurrences(of: "\\s+$", with: "", options: .regularExpression) + "\n"
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    do {
        try recordText.write(to: dir.appendingPathComponent(file), atomically: false, encoding: .windowsCP1251)
    }
    catch { }
}
