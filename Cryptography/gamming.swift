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
        if self.count > 1 {
            var pair = String(self.remove(at: self.startIndex))
            pair += String(self.remove(at: self.startIndex))

            return pair
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
        if used == false {
            text.removeNotCompatiblesChars()
        }

    }
    print("text: \(text)")

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

func handlerCryptoData(text: inout String, encoding: Bool) {
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


    key.makeNewSymbol(positionOf: keyPos)
    key = String(key.dropFirst())
}

// MAIN

key = readFromFile(fileSource: "key gamming.txt", decoding: false)
openText = readFromFile(fileSource: "text gamming.txt", decoding: false)

print("alphabet: \(alphabet)")
print("key: after: \(key)")
print("user text: \(openText)")

var encodedText = ""

if key.count > openText.count {

} else {
    while openText.count != 0 {
        handlerCryptoData(text: &encodedText, encoding: true)
    }
}

print("encoded result: \(encodedText)")
writeToFile(fileSource: "output.txt", text: encodedText)

//let read = readLine()

print("-----")

key = readFromFile(fileSource: "key gamming.txt", decoding: false)
openText = readFromFile(fileSource: "output.txt", decoding: true)

var decodedText = ""

print("key new: \(key)")
print("open text new: \(openText)")

if key.count > openText.count {

} else {
    while openText.count != 0 {
        handlerCryptoData(text: &decodedText, encoding: false)
        print("decoding text: \(decodedText)")
    }
}

print("decoding text: \(decodedText)")
