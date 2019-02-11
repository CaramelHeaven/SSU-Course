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
    var fakeAlphabet = alphabetForWork

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

var key = ""
var text = ""

if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
    key = try String(contentsOf: dir.appendingPathComponent("key 2.txt"), encoding: .utf8)
    text = try String(contentsOf: dir.appendingPathComponent("textUser.txt"), encoding: .utf8)

    key = key.lowercased()
    text = text.lowercased()

    key = removeUselessKeyLetters(key: key) // get refactoring key
    print(key)
    print(alphabetForWork)

    fillTable(key: key)

    for (item, value) in baseTable {
        print("item \(item) value \(value)")
    }

    
}
