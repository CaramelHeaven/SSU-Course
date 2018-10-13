let CRC32: UInt32 = 0x1EDC6F41
let endedNumber: UInt32 = 0x00000001

var container: UInt32 = 0xFFFFFFFF

var bytes = [UInt32]()

if let data = NSData(contentsOfFile: mainFile) {

    var buf = [UInt32](repeating: 0, count: 16)
    data.getBytes(&buf, length: data.length)
    bytes = buf
}

print("buf: \(String(bytes[4], radix: 2))")

for byte in bytes {
    if byte.leadingZeroBitCount > 0 && byte.leadingZeroBitCount != 32 {
        print("chek: \(byte.leadingZeroBitCount)")
        let missingZeros = [String](repeating: "0", count: byte.leadingZeroBitCount)
        var bites = missingZeros.joined() + String(byte, radix: 2)

        var strContainer = String(container, radix: 2)
        for _ in strContainer.indices {
            let substring = strContainer[..<strContainer.index(strContainer.startIndex, offsetBy: 1)]
            let overflowChar = String(substring)
            if overflowChar == "0" {
                strContainer.remove(at: strContainer.startIndex)
                let substring = bites[..<bites.index(bites.startIndex, offsetBy: 1)]
                let character = String(substring)

                strContainer += character
                bites.remove(at: bites.startIndex)

                container = container << 1
                print("<<: \(String(container, radix: 2))")
            } else {
                container = container << 1
                print("<< \(String(container, radix: 2))")
                strContainer.remove(at: strContainer.startIndex)
                let substring = bites[..<bites.index(bites.startIndex, offsetBy: 1)]
                let character = String(substring)

                strContainer += character
                bites.remove(at: bites.startIndex)

                container = container ^ endedNumber
                print("^1 \(String(container, radix: 2))")
                print("^B \(String(CRC32, radix: 2))")
                container = container ^ CRC32
                print("^A \(String(container, radix: 2))")
            }
        }
    }
}
