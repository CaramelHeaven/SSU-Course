//
//  main.swift
//  gamming
//
//  Created by Caramel Heaven on 24/03/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//

import Foundation

let alphabet = "0123456789.,:-!? ();@£$%^&|'/<>ЙЦУКЕЁНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ"
var key = ""
var openText = ""
var keyBinaryFormat = "", openTextBinaryFormat = "", resultBinaryFormat = ""

func showWork() {
    print("key binary: \(keyBinaryFormat)")
    print("op. binary: \(openTextBinaryFormat)")
    print("res binary: \(resultBinaryFormat)")
}

extension String {
    public mutating func removeNotCompatiblesChars() {
        _ = self.map { (char) in
            if !alphabet.contains(char) {
                self = self.replacingOccurrences(of: String(char), with: "")
            }
        }
    }

    public mutating func getLetter() -> String? {
        if self.count > 0 {
            return String(self.remove(at: self.startIndex))
        }
        return nil
    }
}

func getPositionCharFromAlphabet(of char: Character) -> Int? {
    if let kek = alphabet.index(of: char) {
        return alphabet.distance(from: alphabet.startIndex, to: kek)
    }
    return nil
}

func getCharByPositionInAlphabet(position pos: Int) -> Character {
    return alphabet[alphabet.index(alphabet.startIndex, offsetBy: pos)]
}

//return new symbol in alphabet
func makeXORbyByte(value: inout String) -> Int? {
    let count = value.count
    for _ in 0..<count {
        let startVal = Int(String(value.first!))!
        let endVal = Int(String(value.last!))!

        value = String(value.dropFirst())
        value += String(startVal ^ endVal)
    }

    if let number = Int(value, radix: 2) {
        return Int(String(number, radix: 10))
    }
    return nil
}

func formatIntToBinaryAndGetNewValue(value: Int) -> Int? {
    var binaryValue = String(value, radix: 2)
    //add missing 0
    var count = binaryValue.count

    while count != 6 {
        binaryValue.insert("0", at: binaryValue.startIndex)
        count += 1
    }
    return makeXORbyByte(value: &binaryValue)
}

func attachNewSymbolToKey(key: inout String, positionOfKeyValue: Int) {
    if key.count == 1 {
        let positionSymbol = getPositionCharFromAlphabet(of: key.first!)
        let newValuePosition = formatIntToBinaryAndGetNewValue(value: positionSymbol!)

        key.append(getCharByPositionInAlphabet(position: newValuePosition!))
    } else {
        let endSymbolValue = getPositionCharFromAlphabet(of: key.last!)

        key.append(getCharByPositionInAlphabet(position: positionOfKeyValue ^ endSymbolValue!))
    }
}

func addBinaryFormat(key: Int, openText: Int, result: Int) {
    keyBinaryFormat += String(key) + " " // binary format for show xor
    openTextBinaryFormat += String(openText) + " " // binary format for open text
    resultBinaryFormat += String(result) + " "
}

func handlerData(text: inout String, encoding: Bool, lengthKeyLessThanOpenText: Bool) {
    let keyFirst = getPositionCharFromAlphabet(of: key.first!)!
    var openTextFirst: Int?

    if encoding {
        openTextFirst = getPositionCharFromAlphabet(of: openText.first!)!
        openText = String(openText.dropFirst())

        let result = keyFirst ^ openTextFirst!
        text += String(getCharByPositionInAlphabet(position: result))
        addBinaryFormat(key: keyFirst, openText: openTextFirst!, result: result) // for show user output in the future
    } else {
        let letter = openText.getLetter()!
        let result = keyFirst ^ getPositionCharFromAlphabet(of: Character(letter))!

        text += String(getCharByPositionInAlphabet(position: result))
        addBinaryFormat(key: keyFirst, openText: getPositionCharFromAlphabet(of: Character(letter))!, result: result)
    }

    if lengthKeyLessThanOpenText {
        attachNewSymbolToKey(key: &key, positionOfKeyValue: keyFirst)
    }
    key = String(key.dropFirst())
}

func makeGamming(mainOpenText: inout String, encrypt: Bool) {
    var keyLessThanText = false
    if key.count > openText.count {
        let index = key.index(key.startIndex, offsetBy: openText.count)
        key = String(key[..<index])
    } else {
        keyLessThanText = true
    }

    while openText.count != 0 {
        handlerData(text: &mainOpenText, encoding: encrypt, lengthKeyLessThanOpenText: keyLessThanText)
    }
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

func readFromFile(fileSource: String, decodingOpenText: Bool) -> String {
    var text = ""
    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        text = try! String(contentsOf: dir.appendingPathComponent(fileSource), encoding: .utf8)

        text = text.uppercased()
        if !decodingOpenText {
            text.removeNotCompatiblesChars()
        }
    }
    return text
}

//MAIN

key = readFromFile(fileSource: "key gamming.txt", decodingOpenText: false)
openText = readFromFile(fileSource: "text gamming.txt", decodingOpenText: false)
if key.count == 0 || openText.count == 0 {
    print("key or open txt must be > 0")
    exit(0)
}

print("alphabet: \(alphabet)")
print("key: base: \(key)")
print("user text base: \(openText)")

var encodedText = ""
makeGamming(mainOpenText: &encodedText, encrypt: true)

print("encoded text: \(encodedText)")
showWork()

writeToFile(fileSource: "output.txt", text: encodedText)

//wait
let lol = readLine()
keyBinaryFormat = ""; openTextBinaryFormat = ""; resultBinaryFormat = ""

key = readFromFile(fileSource: "key gamming.txt", decodingOpenText: false)
openText = readFromFile(fileSource: "output.txt", decodingOpenText: false)
if key.count == 0 || openText.count == 0 {
    print("key or decoded txt must be > 0")
    exit(0)
}

var decodedText = ""
makeGamming(mainOpenText: &decodedText, encrypt: false)
showWork()

print("decoded text \(decodedText)")
