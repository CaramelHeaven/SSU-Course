import Foundation

//started

let CRC32: UInt32 = 0x1EDC6F41
let plusOne: UInt32 = 0x00000001
var container: UInt32 = 0xFFFFFFFF

var bytes = [UInt32]()

let mainFile = "/Users/sergeyfominov/Documents/TestDirectory/test2.txt"
if let data = NSData(contentsOfFile: mainFile) {

    var buf = [UInt32](repeating: 0, count: 16)
    data.getBytes(&buf, length: data.length)
    bytes = buf
}

var strContainer = String(container, radix: 2)

var containerOfBites: String = ""

for byte in bytes {
    if byte.leadingZeroBitCount > 0 && byte.leadingZeroBitCount != 32 {
        let missingZeros = [String](repeating: "0", count: byte.leadingZeroBitCount)
        let bites = missingZeros.joined() + String(byte, radix: 2)
        containerOfBites = containerOfBites + bites
    }
}
//for _ in 0...32 {
//    containerOfBites += "0"
//}

while strContainer.count > 0 {
    let firstChar = strContainer.first
    let overflowChar = String(firstChar!)
    strContainer.remove(at: strContainer.startIndex)

    var addedSymbol = ""
    if containerOfBites.count != 0 {
        let charFromContainerOfBytes = containerOfBites[..<containerOfBites.index(containerOfBites.startIndex, offsetBy: 1)]
        addedSymbol = String(charFromContainerOfBytes)
        strContainer += addedSymbol
        containerOfBites.remove(at: containerOfBites.startIndex)
    }

    container = container << 1
    
    if addedSymbol != "" && addedSymbol != "0" {
        container = container ^ plusOne
    }

    if overflowChar != "0" {
        container = container ^ CRC32
    }
}

print("result: \(String(container, radix: 16))")
