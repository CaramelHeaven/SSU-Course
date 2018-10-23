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
let file = "second.txt"
var simpleCache = ""
var helping = ""
var hidenText = ""
var sizeHidenText = 0
var allText = ""
var cache = ""
var stayedWord = ""
var binaryStream = ""
var hidenBytes = ""
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
    cache = allText
    allText = allText.replacingOccurrences(of: " |" + hidenText + "|", with: "")
}

let buf: [UInt8] = Array(hidenText.utf8)

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
print("binary stream: \(binaryStream)")
hidenBytes = binaryStream

for index in lineArray.indices {
    var writenLine = ""
    var k = 0
    let separatedLine = lineArray[index].components(separatedBy: " ")
    for i in separatedLine.indices {
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
            stayedWord = separatedLine[i]
            k += 1
            var endLine = i
            var isFirst = true
            endLine += 1
            while endLine != separatedLine.count {
                if isFirst {
                    writenLine += separatedLine[endLine]
                    isFirst = false
                } else {
                    writenLine += " " + separatedLine[endLine]
                }
                endLine += 1
            }
            break
        }
    }
    lineArray[index] = writenLine
    if k == 1 {
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
print("saved word: \(stayedWord)")
print("encoded file")
let ke = readLine()
if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {

    var lineArray = [String]()
    var hidеnBytes = ""

    let fileURL = dir.appendingPathComponent(file)
    hidenText = try String(contentsOf: fileURL, encoding: .utf8)
    allText = hidenText

    allText.enumerateLines { (line, _) in
        lineArray.append(line)
    }

    for line in lineArray {
        let separatedLine = line.split(separator: " ", omittingEmptySubsequences: false)
        for index in separatedLine.indices {
            if separatedLine[index] != stayedWord {
                if separatedLine[index] == "" {
                    hidеnBytes += "1"
                } else {
                    hidеnBytes += "0"
                }
            } else {
                break
            }
        }
    }

    print("hiden bytes \(hidenBytes)")

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
        try cache.write(to: url, atomically: false, encoding: .utf8)
    }
    catch { /* error handling here */ }
    print("decoded file")
}
