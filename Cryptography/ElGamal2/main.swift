//
//  main.swift
//  ElGamal
//
//  Created by CaramelHeaven on 25/03/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//



import Foundation

var globalX = 0
//0123456789.,:-!?ЙЦУКЕЁНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ
let alphabet = ["0": 65, "1": 66, "2": 68, "3": 70, "4": 72, "5": 74, "6": 12, "7": 14, "8": 15, "9": 16, ".": 18, ",": 75, ":": 21, "!": 22, "Й": 24, "Ц": 25, "У": 26, "К": 27, "Е": 28, "Н": 30, "Г": 32, "Ш": 33, "Щ": 34, "З": 35, "Х": 36, "Ъ": 38, "Ф": 39, "Ы": 76, "В": 42, "А": 44, "П": 45, "Р": 46, "О": 48, "Л": 49, "Д": 77, "Ж": 51, "Э": 52, "Я": 54, "Ч": 55, "С": 56, "М": 57, "И": 58, "Ь": 78, "Б": 62, "Ю": 63, "Т": 64, " ": 81]
let alphabetStr = "0123456789.,:-!ЙЦУКЕЁНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ "

struct GroupParts: CustomStringConvertible {
    var array = Array<Int>()

    var description: String {
        let strArr = array.map { String($0) }
        return strArr.joined(separator: "-")
    }
}

struct EncodedBlock: CustomStringConvertible {
    var array = Array<(BInt, BInt)>()

    var description: String {
        let strArr = array.map { "a: " + String($0) + " b: " + String($1) + " " }
        return strArr.joined()
    }
}

extension String {
    public mutating func removeNotCompatiblesChars() {
        _ = self.map { (char) in
            if !alphabetStr.contains(char) {
                self = self.replacingOccurrences(of: String(char), with: "")
            }
        }
    }
}

func subPairs(output: inout String) -> [String] {
    var pairsArray = [String]()


    while output.count > 1 {
        var pair = String(output.remove(at: output.startIndex))
        pair += String(output.remove(at: output.startIndex))
        pairsArray.append(pair)
    }
    return pairsArray
}

func gcdThing(a: Int, b: Int) -> Int {
    if b == 0 {
        return a
    } else {
        return gcdThing(a: b, b: a % b)
    }
}

func powmod(a: Int, b: Int, n: Int) -> Int {
    var a = a
    var b = b

    var result = 1
    while b > 0 {
        if b & 1 == 1 {
            result = result * a % n
            b -= 1
        } else {
            a = a * a % n
            b >>= 1
        }
    }

    return result
}

func solovayStrassenPrimatilyTest(n: Int, k: Int) -> Bool {
    var a = 0, currentGcdThing = 0;
    if n % 2 == 0 {
        return false
    } else {
        for _ in 0..<k {
            a = 2 + Int(arc4random()) % ((n - 1) - 2 + 1)
            currentGcdThing = gcdThing(a: a, b: n)

            if currentGcdThing != 1 {
                return false
            } else {
                if powmod(a: a, b: (n - 1) / 2, n: n) == (a / n % n) {
                    return false
                }
            }
        }
    }
    return true
}

//func getCharByPositionInAlphabet(position pos: Int) -> Character {
//    alphabet
//    return alphabet[alphabet.index(alphabet.startIndex, offsetBy: pos)]
//}

func divideMessageByGroup(msg: inout Array<Int>, prime: Int) -> Array<GroupParts> {
    var arrayGroups = Array<GroupParts>()

    while msg.count > 0 {
        let numbers = msg.first!
        var group = GroupParts()

        if numbers > prime {
            var strNum = String(numbers)
            while strNum.count > 0 {
                let numFirst = Int(String(strNum.first!))
                group.array.append(numFirst!)
                strNum = String(strNum.dropFirst())

                if let nextNum = Int(strNum) {
                    if nextNum < prime {
                        group.array.append(nextNum)
                        break
                    }
                }
            }
        } else {
            group.array.append(numbers)
        }
        msg = Array(msg.dropFirst())

        arrayGroups.append(group)
    }
    return arrayGroups
}

func getPositionCharFromAlphabet(of char: Character) -> Int? {
    return alphabet[String(char)]
}

func readFromFile(fileSource: String, p: Int) -> Array<Int> {
    var groupParts = Array<Int>()

    var txt = ""
    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        var text = try! String(contentsOf: dir.appendingPathComponent(fileSource), encoding: .utf8)
        text = text.uppercased()
        text.removeNotCompatiblesChars()
        print("data text: \(text)")

        for char in text {
            txt += String(getPositionCharFromAlphabet(of: char)!)
        }
        print("base value: \(txt)")
        while txt.count > 0 {
            var lengthNumber = String(p).count

            if lengthNumber == txt.count || lengthNumber > txt.count {
                let index = txt.index(txt.startIndex, offsetBy: txt.count)
                let num = Int(String(txt[..<index]))!

                if p > num {
                    groupParts.append(Int(String(txt[..<index]))!)
                    break
                }
            }

            var indexEnd = txt.index(txt.startIndex, offsetBy: lengthNumber)
            var number = Int(txt[..<indexEnd])!

            if number != p {
                while number > p {
                    lengthNumber -= 1

                    indexEnd = txt.index(txt.startIndex, offsetBy: lengthNumber)
                    number = Int(txt[..<indexEnd])!
                }
            }

            groupParts.append(number)
            txt.removeSubrange(txt.startIndex..<indexEnd)
        }
    }

    return groupParts
}

func readFromDecodedFile(fileSource: String) -> Array<(Int, Int)> {
    var pairs = Array<(Int, Int)>()
    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        let text = try! String(contentsOf: dir.appendingPathComponent(fileSource), encoding: .utf8)
        var array = text.split(separator: "\n")

        if let range = String(array.first!).range(of: "\\d+", options: .regularExpression) {
            print("first x: \(String(array.first!)[range])")
            globalX = Int(String(array.first!)[range])!
        }
        array = Array(array.dropFirst())

        for content in array {
            var part = content.split(separator: ",")
            let a = String(part[0])[String(part[0]).range(of: "\\d+", options: .regularExpression)!]
            let b = String(part[1])[String(part[1]).range(of: "\\d+", options: .regularExpression)!]
            pairs.append((Int(a)!, Int(b)!))
        }
    }

    return pairs
}

func writeToFile(fileSource: String, array: Array<EncodedBlock>, x: Int) {
    var commonStr = ""

    commonStr += "x: " + String(x) + "\n"

    for content in array {
        for group in content.array {
            commonStr += "a: " + String(group.0) + ", b: " + String(group.1) + ", "
        }
        commonStr = String(commonStr.dropLast())
        commonStr = String(commonStr.dropLast())

        commonStr += "\n"
    }

    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        do {
            try commonStr.write(to: dir.appendingPathComponent(fileSource), atomically: false, encoding: .utf8)
        } catch {
            print(error)
        }
    }
}

let prime = 307

print("0 - Encoding, 1 - decoding")
let kak = readLine()

if kak == "0" {
    if solovayStrassenPrimatilyTest(n: prime, k: 100) {
        print("Number is prime")
        if prime < 10 {
            print("Prime should be > 9")
            exit(0)
        }

        let g = GenerateKey.instance.findPrimiteRoot(p: prime)!
        let x = GenerateKey.instance.getRandomX(p: prime)

        //count y
        let y = GenerateKey.instance.getY(g: g, x: x, p: prime)

        //divide by groups
        print("Start Encoding")
        let message = readFromFile(fileSource: "dataElGamal.txt", p: prime)

        print("message: \(message)")

        //start encoding
        var encodingMsg = Array<EncodedBlock>()

        for number in message {
            let k = GenerateKey.instance.getSessionKeyK(p: prime)
            let a = ElGamal.instance.findA(g: g, k: k, p: prime)
            var encNumInBlock = EncodedBlock()
            let b = ElGamal.instance.findB(y: y, k: k, m: number, p: prime)

            print("a: \(a) and b: \(b)")
            encNumInBlock.array.append((a, b))

            encodingMsg.append(encNumInBlock)
        }

        print("encoding Message: \(encodingMsg)")

        writeToFile(fileSource: "outputElGamal.txt", array: encodingMsg, x: x)


    } else {
        print("Number isn't prime")
    }
} else if kak == "1" {
    //start decoding
    _ = readLine()

    let outputData = readFromDecodedFile(fileSource: "outputElGamal.txt")

    print("message output: \(outputData)")

    var decodedMessage = ""

    for tuple in outputData {
        print("x: \(globalX), a: \(tuple.0), b: \(tuple.1), prime: \(prime)")
        decodedMessage += ElGamal.instance.decodingMessage(a: BInt(tuple.0), b: BInt(tuple.1), x: globalX, p: prime)
    }

    print("decoded Message: \(decodedMessage)")

    let pairs = subPairs(output: &decodedMessage)

    print("pairs: \(pairs)")

    var completedMessage = ""
    for pair in pairs {
        for (key, value) in alphabet {
            if value == Int(pair) {
                completedMessage += key
                break
            }
        }
    }

    print("completed: \(completedMessage)")
} else {
    print("nothing")
    exit(0)
}


