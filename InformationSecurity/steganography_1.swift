import Foundation

extension String {
    func between(_ left: String, _ right: String) -> String? {
        guard let leftRange = range(of: left), let rightRange = range(of: right, options: .backwards)
            , leftRange.upperBound <= rightRange.lowerBound
            else { return nil }

        let sub = self[leftRange.upperBound...]
        let closestToLeftRange = sub.range(of: right)!
        return String(sub[..<closestToLeftRange.lowerBound])
    }
}

func insertHidenText(stroke: String, hidenWord: String, cache: String) -> String {
    var completedStroke = ""
    var indicesWord = 1
    var counter = 0
    for index in stroke.indices {
        if stroke[index] != cache[index] && indicesWord == 1 {
            completedStroke += stroke[..<stroke.index(stroke.startIndex, offsetBy: counter)]
            completedStroke += hidenWord
            indicesWord += 1
            completedStroke += " " + String(stroke.suffix(stroke.count - counter))
            break
        }
        counter += 1
    }
    if completedStroke.last! == " " {
        completedStroke = String(completedStroke.dropLast())
    }
    return completedStroke
}

// MAIN
let file = "test.txt"
var simpleCache = ""
var helping = ""
var hidenText = ""
var sizeHidenText = 0
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

sizeHidenText = binaryStream.count

var lineArray = [String]()
allText.enumerateLines { (line, _) in
    lineArray.append(line)
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
print("encoded file")
let ke = readLine()
if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {

    var lineArray = [String]()
    var hidenBytes = ""

    let fileURL = dir.appendingPathComponent(file)
    hidenText = try String(contentsOf: fileURL, encoding: .utf8)
    allText = hidenText

    allText.enumerateLines { (line, _) in
        lineArray.append(line)
    }

    var counter = 0
    for line in lineArray {
        if counter == 8 {
            hidenBytes += " "
            counter = 0
        }
        if sizeHidenText > 0 {
            if String(line.last!) == " " {
                hidenBytes += "1"
            } else {
                hidenBytes += "0"
            }
            sizeHidenText -= 1
            counter += 1
        }
    }

    let hidenWord = hidenBytes.split(separator: " ").compactMap {
        String(Unicode.Scalar(Int($0, radix: 2)!)!)
    }.joined(separator: "")

    var tem = 0
    var completed = ""
    allText.enumerateLines { (line, _) in
        var myLine = ""
        if line.contains(helping) && tem == 0 {
            completed.append(insertHidenText(stroke: line, hidenWord: hidenWord, cache: simpleCache))
            completed += "\n"
            tem += 1
        } else {
            if String(line.last!) == " " {
                myLine = line
                completed.append(String(myLine.dropLast()) + "\n")
            } else {
                completed.append(line + "\n")
            }

        }
    }
    let url = dir.appendingPathComponent(file)

    do {
        try completed.write(to: url, atomically: false, encoding: .utf8)
    }
    catch { /* error handling here */ }
    print("decoded file")
}
