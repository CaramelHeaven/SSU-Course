import Foundation

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
var simpleCache = ""
var helping = ""
var hidenText = ""
var allText = ""
print("Enter the instruction, sir ")

let instruction = readLine()

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {

    let fileURL = dir.appendingPathComponent(file)
    hidenText = try String(contentsOf: fileURL, encoding: .utf8)
    allText = hidenText
    hidenText = hidenText.between("|", "|")!
    allText.enumerateLines { (line, _) in
        if line.contains(hidenText) {
            simpleCache += line
            helping = simpleCache.replacingOccurrences(of: " |" + hidenText + "|", with: "")
            return
        }
    }
    allText = allText.replacingOccurrences(of: " |" + hidenText + "|", with: "")
}

let buf: [UInt8] = Array(hidenText.utf8)

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
    print(binaryStream)
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
var myOwnText = ""
for line in lineArray {
    myOwnText = myOwnText + line + "\n"
}

if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
    let fileURL = dir.appendingPathComponent(file)

    do {
        try myOwnText.write(to: fileURL, atomically: false, encoding: .utf8)
    }
    catch { /* error handling here */ }
}
let ke = readLine()
if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {

    var lineArray = [String]()
    var hidenBytes = ""
    var futurePoem = ""
    var i = 0

    let fileURL = dir.appendingPathComponent(file)
    hidenText = try String(contentsOf: fileURL, encoding: .utf8)
    allText = hidenText

    allText.enumerateLines { (line, _) in
        if line.contains(helping) && i == 0 {
            print("append simple cache: \(line) and: \(simpleCache)")
            lineArray.append(simpleCache)
            i = i + 1
        } else {
            lineArray.append(line)
        }
    }
    
    print(lineArray)

    for line in lineArray {
        if String(line.last!) == " " {
            hidenBytes += "1"
        } else {
            hidenBytes += "0"
        }
        futurePoem = futurePoem + line + "\n"
    }

    let url = dir.appendingPathComponent(file)

    do {
        try futurePoem.write(to: url, atomically: false, encoding: .utf8)
    }
    catch { /* error handling here */ }
}
