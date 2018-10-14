import Foundation

print("Hello, World!")

public extension String {
    func between(_ left: String, _ right: String) -> String? {
        guard let leftRange = range(of: left), let rightRange = range(of: right, options: .backwards)
            , leftRange.upperBound <= rightRange.lowerBound
            else { return nil }

        let sub = self[leftRange.upperBound...]
        let closestToLeftRange = sub.range(of: right)!
        return String(sub[..<closestToLeftRange.lowerBound])
    }
}

let file = "test.txt"
var mainText = ""
var allText = ""

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {

    let fileURL = dir.appendingPathComponent(file)

    mainText = try String(contentsOf: fileURL, encoding: .utf8)
    allText = mainText
    mainText = mainText.between("|", "|")!
    print("my test: \(mainText)")
}

let buf: [UInt8] = Array(mainText.utf8)

var binaryStream = ""

for kek in buf {
    if kek.leadingZeroBitCount > 0 {
        let missingZeros = [String](repeating: "0", count: kek.leadingZeroBitCount)
        binaryStream += missingZeros.joined() + String(kek, radix: 2)
    }
}

print(binaryStream)

var lineArray = [String]()
allText.enumerateLines { (line, _) in
    lineArray.append(line)
}


for line in lineArray.indices {
    let binaryChar = binaryStream[..<binaryStream.index(binaryStream.startIndex, offsetBy: 1)]
    binaryStream.remove(at: binaryStream.startIndex)

    if String(binaryChar) != "0" {
        lineArray[line] += " "
    }
}

for line in lineArray {
    print(line)
}
