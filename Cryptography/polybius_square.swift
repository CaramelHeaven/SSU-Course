//
//  main.swift
//  polybius_square
//
//  Created by Caramel Heaven on 24/01/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//
import Foundation

extension String {
    func removeUselessCharacters(alphabet: String, key: String) -> String {
        var resultString = alphabet

        for item in key {
            if alphabet.contains(item) {
                resultString = resultString.replacingOccurrences(of: String(item), with: "")
            }
        }

        return resultString
    }
}

struct Matrix {
    let rows: Int, columns: Int
    var grid: [String]

    init(rows: Int, columns: Int) {
        self.rows = rows
        self.columns = columns
        grid = Array(repeating: "", count: rows * columns)
    }

    func indexIsValid(row: Int, column: Int) -> Bool {
        return row >= 0 && row < rows && (column >= 0) && column < columns
    }

    subscript(row: Int, column: Int) -> String {
        get {
            return grid[(row * columns) + column]
        }
        set {
            grid[(row * columns) + column] = newValue
        }
    }

    func findCharacterPosition(single: String) -> (String, String) {
        var row = 0, column = 0
        for i in 0..<self.rows {
            for j in 0..<self.columns {
                if self[i, j] == single {
                    row = i; column = j

                    break
                }
            }
        }

        return (String(row), String(column))
    }

    func findCharByPositionInMatrix(row: Int, column: Int) -> String {
        return self[row, column]
    }

    func sout() {
        var rows = ""
        for j in 0..<self.columns {
            rows += String(j) + " "
        }
        print("   \(rows)")

        for i in 0..<self.rows {
            var columns = ""
            for j in 0..<self.columns {
                columns += self[i, j] + " "
            }
            print("\(i): \(columns)")
        }
    }
}

func findRowsAndColumns(commonCount: Int) -> (Int, Int) {
    let rowsCount = (Int(sqrt(Double(commonCount))))
    var columnsCount = rowsCount

    var residue = commonCount - (rowsCount * rowsCount) - 1

    while residue > rowsCount {
        columnsCount += 1
        residue -= rowsCount
    }

    return (rowsCount, columnsCount)
}

func initialMatrix(matrix: inout Matrix, keyData: String, alphabet: String) {
    var key = keyData
    var residueAlphabet = alphabet.removeUselessCharacters(alphabet: alphabet, key: key)

    for i in 0..<matrix.rows {
        for j in 0..<matrix.columns {
            if key.count > 0 {
                let char = String(key.first!)

                matrix[i, j] = char
                key.removeFirst()
            } else if residueAlphabet.count > 0 {
                let char = String(residueAlphabet.first!)

                matrix[i, j] = char
                residueAlphabet.removeFirst()
            }
        }
    }
}

func refactoringString(str: inout String) {
    var set = Set<Character>()
    str = str.filter { set.insert($0).inserted }
}

func buildMatrix(alphabet: String, keyMain: String) -> Matrix {
    let data = findRowsAndColumns(commonCount: alphabet.count + keyMain.count) //data row and columns
    var matrix = Matrix(rows: data.0, columns: data.1)

    initialMatrix(matrix: &matrix, keyData: keyMain, alphabet: alphabet)

    return matrix
}

//initial data
var alphabet = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮйцукенгшщзхъфывапролджэячсмитьбю"
var enteringText = alphabet
alphabet = String(alphabet.sorted()) + "0123456789.,:-!? "

//MARK - MAIN

func checkingEnterUserKey(str: String) -> Bool {
    for item in str {
        if !alphabet.contains(item) {
            return false
        }//Алалалалалал
    }
    return true
}

func checkingEnterUserText(string: String) -> Bool {
    print("checkign string: \(string)")
    for item in string {
        if !enteringText.contains(item) {
            return false
        }
    }
    return true
}


print("Choose action")
print("1 - encoded data")
print("2 - decoded data")
let action = readLine()

if action == "1" {
    // MARK - ENCODER

    print("Enter you key")
    var keyMain = readLine()!

    while(!checkingEnterUserKey(str: keyMain)) {
        print("You entered non compatible key, try again: ")
        keyMain = readLine()!
    }

    refactoringString(str: &keyMain)

    print("Enter you text for encode")
    var textFromUser = readLine()!

    while(!checkingEnterUserText(string: textFromUser)) {
        print("You entered non compatible text, try again: ")
        textFromUser = readLine()!
    }

    var encodeText = ""

    let matrix = buildMatrix(alphabet: alphabet, keyMain: keyMain)
    matrix.sout()

    for char in textFromUser {
        let (row, column) = matrix.findCharacterPosition(single: String(char)) //haha
        encodeText += (row + column)
    }

    print("encoded: \(encodeText)")

    //Create file and save data
    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        do {
            try encodeText.write(to: dir.appendingPathComponent("output.txt"), atomically: false, encoding: .utf8)
        }
        catch {
            print("something error: \(error)")
        }
    }

} else if action == "2" {
    // MARR - ECONDER

    print("Enter a key for decode file")
    var keyMain = readLine()!

    while(!checkingEnterUserKey(str: keyMain)) {
        print("You entered non compatible text, enter again: ")
        keyMain = readLine()!
    }

    refactoringString(str: &keyMain)

    var outputText = ""
    var decodedText = ""

    if let dir = FileManager.default.urls(for: .desktopDirectory, in: .userDomainMask).first {
        outputText = try String(contentsOf: dir.appendingPathComponent("output.txt"), encoding: .utf8)
    }

    let matrix = buildMatrix(alphabet: alphabet, keyMain: keyMain)

    matrix.sout()

    var pairsArray = [String]()

    while outputText.count > 1 {
        var pair = String(outputText.remove(at: outputText.startIndex))
        pair += String(outputText.remove(at: outputText.startIndex))

        pairsArray.append(pair)
    }

    print("pairs: \(pairsArray)")

    for item in pairsArray {
        let char = matrix.findCharByPositionInMatrix(row: Int(String(item.first!))!, column: Int(String(item.last!))!)

        decodedText += char
    }

    print("Decoded: \(decodedText)")
} else {
    print("Exit from programm")
}
