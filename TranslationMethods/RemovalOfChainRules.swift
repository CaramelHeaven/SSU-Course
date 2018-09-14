//
//  main.swift
//  TranslationMethods
//
//  Created by on 13/09/2018.
//  Copyright © 2018 CaramelHeaven. All rights reserved.
//

import Foundation
var oneLetters: [String] = []
var manyRules: [String] = []
var base: Dictionary<String, String> = [:]

extension String {
    
    func removeCharacters(from forbiddenChars: CharacterSet) -> String {
        let passed = self.unicodeScalars.filter { !forbiddenChars.contains($0) }
        return String(String.UnicodeScalarView(passed))
    }
    
    func removeCharacters(from: String) -> String {
        return removeCharacters(from: CharacterSet(charactersIn: from))
    }
}

var aff: [String] = []
func example(rule: String, rootRule: String) {
    let rules = base[rule]
    var check = rules!.components(separatedBy: "|")
    for item in check {
        if item.count == 1 {
            example(rule: item, rootRule: rootRule)
            aff.append(item)
        } else {
            var gg = base[rootRule]
            let trim = base[rootRule]?.components(separatedBy: "|")
            for t in trim! {
                if t.count == 1 {
                    base[rootRule]! = base[rootRule]!.removeCharacters(from: t)
                }
            }
            gg?.append("|")
            gg?.append(item)
            base[rootRule] = gg
        }
    }
}

func provideChainRules(rules: [String]) {
    for rule in rules {
        let letters = rule.components(separatedBy: "->")
        base[letters[0].trimmingCharacters(in: .whitespaces)] = letters[1].trimmingCharacters(in: .whitespaces)
        print("base: \(letters[0]) and \(letters[1])")
    }

    for (one, two) in base {
        let trimming = two.components(separatedBy: "|")
        for item in trimming {
            if item.count == 1 {
                example(rule: item, rootRule: one)
            }
        }
    }

    for (t, k) in base {
        print("after: \(t), value \(k)")
    }


}
//            for letter in getLetters {
//                let trimmed = letter.trimmingCharacters(in: .whitespaces)
//                // print("trim: \(trimmed)")
//                if trimmed.count == 1 {
//                    oneLetters.append(letter)
//                }
//            }

//        let components = rule.components(separatedBy: "|")
//        for component in components {
//            print("component \(component)")
//            if component.count == 1 {
//                oneLetters.append(component)
//            } else {
//                let startedLetter = String(rule[..<rule.index(rule.startIndex, offsetBy: 1)])
//                let one = component.index(rule.startIndex, offsetBy: 0)
//                let end = component.index(rule.endIndex, offsetBy: 0)
//                //      let longRule = String(component[one..<end])
//                base[startedLetter] = component
//            }
//        }
//  }


var rules: [String] = []

rules = ["S -> SA|R|W", "R -> A|W", "A -> aR", "W -> G", "G -> T", "T -> ke"]

provideChainRules(rules: rules)
