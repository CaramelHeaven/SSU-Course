//
//  main.swift
//  letters_frequency
//
//  Created by Caramel Heaven on 18/01/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//

import Foundation

let russianAlphabet = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁё"

func counterLettersFrequency(mainFile: String) throws {
    let textFromFile = try String(contentsOfFile: mainFile, encoding: String.Encoding.utf8)
    for letter in textFromFile {
        if russianAlphabet.contains(letter) {
            if let _ = mapLetters[letter] {
                var value = mapLetters[letter]!
                value += 1

                mapLetters.updateValue(value, forKey: letter)
            }
            else {
                mapLetters[letter] = 1
            }
        }
    }

    var result = [Character: Double]()

    for (key, _) in mapLetters {
        let value = Double(mapLetters[key]!) / 1000.0

        result[key] = value
    }

    let sortedKeys = Array(result.keys).sorted()
    //sout result
    print("Table of letters frequency:")

    for letter in sortedKeys {
        print("\(letter) := \(String(format: "%.3f", result[letter]!))")
    }
}

// MARK MAIN
var mapLetters = [Character: Int]()
var textFromFile = ""

var userCase = 0

while(userCase == 0) {
    print("Enter a path to file: ")
    let mainFile = readLine()

    do {
        try counterLettersFrequency(mainFile: mainFile!)
        userCase += 1
    } catch {
        print("File not found, do u want to try again? (y/n)")
        let read = readLine()!

        if read == "y" || read == "ye" || read == "yes" || read == "yup" {
            //contains
        } else {
            userCase += 1
        }
    }
}

print("exit")
