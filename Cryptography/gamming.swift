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

var encodedBinaryKeyData = ""
var encodedBinaryOpenTextData = ""

func convert(_ str: String, fromRadix r1: Int, toRadix r2: Int) -> String? {
    if let num = Int(str, radix: r1) {
        return String(num, radix: r2)
    } else {
        return nil
    }
}

func fillMissingNulls(value: inout String) {
    var count = value.count

    while count != 6 {
        value.insert("0", at: value.startIndex)
        count += 1
    }
}

func formatIntToBinaryAndMakeNewSymbol(value: Int) -> String {
    var binaryValue = String(value, radix: 2)
    fillMissingNulls(value: &binaryValue)
    print("im here")

    let count = binaryValue.count
    for _ in 0..<count {
        let startValue = Int(String(binaryValue.first!))!
        let endValue = Int(String(binaryValue.last!))!

        binaryValue = String(binaryValue.dropFirst())

        binaryValue += String(startValue | endValue)
    }

    return binaryValue
}

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
    }

    public mutating func makeNewSymbol(positionOf startIndex: Int) {
        print("make new symbol: \(self.count)")
        if self.count == 1 {
            let positionFromAlphabet = self.getPositionCharFromAlphabet(of: self.first!)!
            let newBinaryChar = formatIntToBinaryAndMakeNewSymbol(value: positionFromAlphabet)
            let newChar = convert(newBinaryChar, fromRadix: 2, toRadix: 10)!

            print("newChar: \(newChar)")
            self.append(getCharByPositionInAlphabet(position: Int(newChar)!))
        } else {
            let endIndex = self.getPositionCharFromAlphabet(of: self.last!)!
            let newChar = getCharByPositionInAlphabet(position: startIndex ^ endIndex)

            self.append(newChar)
        }
    }

    public mutating func getLetter() -> String? {
        if self.count > 0 {
            return String(self.remove(at: self.startIndex))
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
    encodedBinaryKeyData += String(keyPos, radix: 2) //add to common view
    var textPos: Int? // can be one symbol or for decoding - two.

    if encoding {
        textPos = openText.getPositionCharFromAlphabet(of: openText.first!)!
        encodedBinaryOpenTextData += String(textPos!, radix: 2)

        openText = String(openText.dropFirst())
        let result = keyPos ^ textPos!

        text += String(getCharByPositionInAlphabet(position: result))
    } else {
        let encodedLetter = openText.getLetter()!
        let result = keyPos ^ encodedLetter.getPositionCharFromAlphabet(of: Character(encodedLetter))!

        text += String(getCharByPositionInAlphabet(position: result))
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

print("common key: \(encodedBinaryKeyData)")
print("common open text: \(encodedBinaryOpenTextData)")

writeToFile(fileSource: "output.txt", text: encodedText)

//let read = readLine()

print("-----")

let lol = readLine()
key = readFromFile(fileSource: "key gamming.txt", decoding: false)
openText = readFromFile(fileSource: "output.txt", decoding: false)

print("encoded text: \(openText)")
var decodedText = ""
makeGamming(mainText: &decodedText, encryptData: false)

print("decoding text: \(decodedText)")
