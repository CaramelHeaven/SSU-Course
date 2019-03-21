//
//  main.swift
//  gamming
//
//  Created by Caramel Heaven on 25/02/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//

import Foundation

let alphabet = "0123456789.,:-!? ();@£$%^&|'/<>ЙЦУКЕЁНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ"
var key = ""
var openText = ""

extension String {
    public func getPositionCharFromAlphabet(of char: Character) -> Int? {
        if let kek = alphabet.index(of: char) {
            return alphabet.distance(from: alphabet.startIndex, to: kek)
        }
        return nil
    }

    public mutating func removeNotCompatiblesChars() {
        _ = self.map { (char) in
            if !alphabet.contains(char) {
                self = self.replacingOccurrences(of: String(char), with: "")
            }
        }
        var set = Set<Character>()
        self = self.filter { set.insert($0).inserted }
    }

    public mutating func makeNewSymbol(positionOf startIndex: Int) {
        let endIndex = self.getPositionCharFromAlphabet(of: self.last!)!
        let newChar = getCharByPositionInAlphabet(position: startIndex ^ endIndex)

        self.append(newChar)
    }

    public mutating func firstPair() -> String? {
        if self.count > 0 {
            let firstLetter = String(self.remove(at: self.startIndex))

            var result = ""
            if self.count > 0 {
                result = firstLetter + String(self.first!)
            } else {
                result = firstLetter
            }

            if let value = Int(result) {
                if value > alphabet.count {
                    return firstLetter
                } else {
                    self = String(self.dropFirst())
                }
            }

            return result
        }
        return nil
    }
}

func getCharFromAlphabetByIndex(of position: Int) -> String {
    let index = alphabet.index(alphabet.startIndex, offsetBy: position)
    return String(alphabet[index])
}

func getCharByPositionInAlphabet(position pos: Int) -> Character {
    return alphabet[alphabet.index(alphabet.startIndex, offsetBy: pos)]
}

func readFromFile(fileSource: String, decoding used: Bool) -> String {
    var text = ""
    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        text = try! String(contentsOf: dir.appendingPathComponent(fileSource), encoding: .utf8)

        text = text.uppercased()
        if !used {
            text.removeNotCompatiblesChars()
        }

    }
    return text
}

func writeToFile(fileSource: String, text: String) {
    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        do {
            try text.write(to: dir.appendingPathComponent(fileSource), atomically: false, encoding: .utf8)
        } catch {
            print(error)
        }
    }
}

func handlerCryptoData(text: inout String, encoding: Bool, lenghKeyLessThanOpenText: Bool) {
    let keyPos = key.getPositionCharFromAlphabet(of: key.first!)!
    var textPos: Int? // can be one symbol or for decoding - two.

    if encoding {
        textPos = openText.getPositionCharFromAlphabet(of: openText.first!)!
        openText = String(openText.dropFirst())
        text += String(keyPos ^ textPos!)
    } else {
        textPos = Int(openText.firstPair()!)
        text += getCharFromAlphabetByIndex(of: keyPos ^ textPos!)
    }

    if lenghKeyLessThanOpenText {
        key.makeNewSymbol(positionOf: keyPos)
    }
    key = String(key.dropFirst())
}

func makeGamming(mainText: inout String, encryptData: Bool) {
    if key.count > openText.count {
        let index = key.index(key.startIndex, offsetBy: openText.count)
        key = String(key[..<index])

        while openText.count != 0 {
            handlerCryptoData(text: &mainText, encoding: encryptData, lenghKeyLessThanOpenText: false)
        }
    } else {
        while openText.count != 0 {
            handlerCryptoData(text: &mainText, encoding: encryptData, lenghKeyLessThanOpenText: true)
        }
    }
}

// MAIN

key = readFromFile(fileSource: "key gamming.txt", decoding: false)
openText = readFromFile(fileSource: "text gamming.txt", decoding: false)

print("alphabet: \(alphabet)")
print("key: base: \(key)")
print("user text base: \(openText)")

var encodedText = ""
makeGamming(mainText: &encodedText, encryptData: true)

print("encoded result: \(encodedText)")
writeToFile(fileSource: "output.txt", text: encodedText)

//let read = readLine()

print("-----")

let lol = readLine()
key = readFromFile(fileSource: "key gamming.txt", decoding: false)
openText = readFromFile(fileSource: "output.txt", decoding: true)

print("encoded text: \(openText)")
var decodedText = ""
makeGamming(mainText: &decodedText, encryptData: false)

print("decoding text: \(decodedText)")
