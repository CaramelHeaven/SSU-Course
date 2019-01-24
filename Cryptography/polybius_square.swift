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

//Remove duplicated symbols
extension RangeReplaceableCollection where Element: Hashable {
    var squeezed: Self {
        var set = Set<Element>()
        return filter { set.insert($0).inserted }
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
            assert(indexIsValid(row: row, column: column), "Index out of range")
            return grid[(row * columns) + column]
        }
        set {
            assert(indexIsValid(row: row, column: column), "Index out of range")
            grid[(row * columns) + column] = newValue
        }
    }
    
    func findCharacter(char: String) -> (Int, Int) {
        var row = 0, column = 0
        for i in 0..<self.rows {
            for j in 0..<self.columns {
                if self[i, j] == char {
                    row = i; column = j
                    
                    break
                }
            }
        }
        if row == 0 && column == 0 {
            return (self.rows, self.columns)
        }
        return (row, column)
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
    
    var residue = commonCount - (rowsCount * rowsCount)
    
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


//initial data
let alphabet = "АаБбВвГгДдЕеЁёЖжЗзИиӢйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя.,:-!?0123456789"
var keyMain = "аби4вабив.32590"

// MARK - MAIN
let mainFile = "main

let baseText = try? String(contentsOfFile: mainFile, encoding: String.Encoding.utf8)
var encodeText = ""
var decodeText = ""

var key = keyMain.squeezed //non duplicated
let data = findRowsAndColumns(commonCount: alphabet.count + key.count) //data row and columns

var matrix = Matrix(rows: data.0, columns: data.1)

initialMatrix(matrix: &matrix, keyData: key, alphabet: alphabet)

matrix.sout()

for char in baseText! {
    let data = matrix.findCharacter(char: String(char))
    
    if data.0 == matrix.rows && data.1 == matrix.columns {
        encodeText += " "
    } else {
        let encodeSymbol = String(data.0) + String(data.1)
        
        encodeText += encodeSymbol
    }
}

print("encoding")
print(encodeText)
