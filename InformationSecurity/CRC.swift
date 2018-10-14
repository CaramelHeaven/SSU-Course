import Foundation

//started

let CRC32: UInt32 = 0x1EDC6F41
let plusOne: UInt32 = 0x00000001
let plusZero: UInt32 = 0x00000000

var container: UInt32 = 0xFFFFFFFF

var bytes = [UInt32]()

if let data = NSData(contentsOfFile: mainFile) {

    var buf = [UInt32](repeating: 0, count: 16)
    data.getBytes(&buf, length: data.length)
    bytes = buf
}

print("buf: \(String(bytes[4], radix: 2))")
var strContainer = String(container, radix: 2)

var containerOfBites: String = ""

for byte in bytes {
    if byte.leadingZeroBitCount > 0 && byte.leadingZeroBitCount != 32 {
        print("chek: \(byte.leadingZeroBitCount)")
        let missingZeros = [String](repeating: "0", count: byte.leadingZeroBitCount)
        let bites = missingZeros.joined() + String(byte, radix: 2)
        containerOfBites = containerOfBites + bites
    }
}
print(containerOfBites)

while strContainer.count > 0 {
    let firstChar = strContainer[..<strContainer.index(strContainer.startIndex, offsetBy: 1)]
    let overflowChar = String(firstChar)

    strContainer.remove(at: strContainer.startIndex)

    var addedSymbol = ""
    if containerOfBites.count > 0 {
        let charFromContainerOfBytes = containerOfBites[..<containerOfBites.index(containerOfBites.startIndex, offsetBy: 1)]
        addedSymbol = String(charFromContainerOfBytes)
        strContainer += addedSymbol
        containerOfBites.remove(at: containerOfBites.startIndex)
    }

    if overflowChar == "0" {
        container = container << 1
    } else {
        container = container << 1
        if addedSymbol == "0" {
            container = container ^ plusZero
        } else {
            container = container ^ plusOne
            container = container ^ CRC32
        }
    }
}

print("result: \(String(container, radix: 16))")
