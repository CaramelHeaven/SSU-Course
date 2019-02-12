//
//  main.swift
//  cipherDora
//
//  Created by Caramel Heaven on 11/02/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//

import Foundation

var alphabetForWork = "qwertyuiopasdfghjklzxcvbnm"
var englishAlphabet = "qwertyuiopasdfghjklzxcvbnm"
alphabetForWork = String(alphabetForWork.sorted())
englishAlphabet = String(englishAlphabet.sorted())

var baseTable = [[Int]: String]() // our table encoding

extension String {
    public func index(of char: Character) -> Int? {
        if let idx = characters.index(of: char) {
            return characters.distance(from: startIndex, to: idx)
        }
        return nil
    }
}

func setRemainLetters(keyLetters: String) {
    for item in keyLetters {
        if alphabetForWork.contains(item) {
            alphabetForWork = alphabetForWork.replacingOccurrences(of: String(item), with: "")
        }
    }
}

func cleanUpUserText(text: inout String) {
    for item in text {
        if !englishAlphabet.contains(item) {
            text = text.replacingOccurrences(of: String(item), with: "")
        }
    }
}

func removeUselessKeyLetters(key: String) -> String {
    var refactorKey = ""
    var counter = 0 // counter which we need to count first 9 symbols from key
    var fakeAlphabet = englishAlphabet

    for char in key {
        if fakeAlphabet.contains(char) {
            if counter >= 9 {
                break
            } else {
                let convert = String(char)

                refactorKey += convert
                fakeAlphabet = fakeAlphabet.replacingOccurrences(of: convert, with: "")

                counter += 1
            }
        }
    }

    setRemainLetters(keyLetters: refactorKey)

    return refactorKey
}

//sout baseTable
func sout() {
    let kek = baseTable.sorted { $0.key.count > $1.key.count }

    for (item, value) in kek {
        print("\(item) : \(value)")
    }
}

func fillTable(key: String) {
    let firstLine = [4, 5, 6, 7, 8, 9]
    let secondLine = [2, 3]
    let thirdLine = [1]

    let index = alphabetForWork.index(alphabetForWork.startIndex, offsetBy: alphabetForWork.count / 2)

    let firstHalf = String(alphabetForWork[..<index])
    let secondHalf = alphabetForWork.replacingOccurrences(of: firstHalf, with: "")

    baseTable[firstLine] = key
    baseTable[secondLine] = firstHalf
    baseTable[thirdLine] = secondHalf
}

func encodingByTable(char: Character) -> (String?, String?) {
    for (key, value) in baseTable {
        if value.contains(char) {
            let rowKey = key.randomElement()!
            let columnKey = value.index(of: char)! + 1 //we need to plus 1 for better result

            return (String(rowKey), String(columnKey))
        }
    }

    return (nil, nil)
}

func decodingByTable(pair: String) -> String? {
    let first = Int(String(pair.first!))!
    let second = Int(String(pair.last!))!

    for (key, value) in baseTable {
        if key.contains(first) {
            let index = value.index(value.startIndex, offsetBy: (second - 1)) // we need to minus 1. @see encodingByTable
            return String(value[index])
        }
    }

    return nil
}


var key = ""
var text = ""
var output = ""

var encoding = ""
var decoding = ""

if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
    key = try String(contentsOf: dir.appendingPathComponent("key 2.txt"), encoding: .utf8)
    text = try String(contentsOf: dir.appendingPathComponent("textUser.txt"), encoding: .utf8)

    key = key.lowercased()
    text = text.lowercased()

    key = removeUselessKeyLetters(key: key) // get refactoring key
    cleanUpUserText(text: &text)

    fillTable(key: key)

    sout()

    for char in text {
        let (row, column) = encodingByTable(char: char)
        encoding += row! + column!

        let fantom = arc4random_uniform(2)
        if fantom == 0 {
            encoding += String(fantom)
        }
    }

    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        do {
            try encoding.write(to: dir.appendingPathComponent("output.txt"), atomically: false, encoding: .utf8)
        }
        catch {
            print("something error: \(error)")
        }
    }
}

if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
    key = try String(contentsOf: dir.appendingPathComponent("key 2.txt"), encoding: .utf8)
    output = try String(contentsOf: dir.appendingPathComponent("output.txt"), encoding: .utf8)

    key = key.lowercased()
    key = removeUselessKeyLetters(key: key)
    output = output.replacingOccurrences(of: "0", with: "")

    fillTable(key: key)

    sout()

    var pairsArray = [String]()

    while output.count > 1 {
        var pair = String(output.remove(at: output.startIndex))
        pair += String(output.remove(at: output.startIndex))

        pairsArray.append(pair)
    }

    for pair in pairsArray {
        let letter = decodingByTable(pair: pair)

        decoding += letter!
    }

    print("textus: \(text)")
    print("output: \(decoding)")
}
