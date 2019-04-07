//
//  GenerateKey.swift
//  ElGamal2
//
//  Created by CaramelHeaven on 28/03/2019.
//  Copyright © 2019 CaramelHeaven. All rights reserved.
//

import Foundation

class ElGamal {
    static let instance = ElGamal()

    private init() {
    }

    //find a = g^k mod p
    public func findA(g: Int, k: Int, p: Int) -> BInt {
        print("------Count A-------")
        let bigG = BInt(g)
        var a = bigG ** k
        print("g: \(bigG), k: \(k), p: \(p)")
        a %= p
        print("result: \(a)")

        return a
    }

    //find b = (y^k) * M mod p
    public func findB(y: BInt, k: Int, m: Int, p: Int) -> BInt {
        print("------Count B-------")
        print("y: \(y), k: \(k), message: \(m), prime: \(p)")
        let yK = (y ** k)
        let b = yK * m
        
        let result = b % p
        return result
    }

    public func decodingMessage(a: BInt, b: BInt, x: Int, p: Int) -> String {
        let pow = a ** (p - 1 - x)
        
        let result = b * pow
        let kek = result % p
        print("result: \(kek)")

        return String(kek)
    }
}

class GenerateKey {
    static let instance = GenerateKey()

    private init() {
    }

    //session key for encdoing user message, 1 < k < prime - 1
    public func getSessionKeyK(p: Int) -> Int {
        var k = 0
        while k <= 2 {
            k = Int(arc4random_uniform(UInt32(p - 1)))
        }

        return k
    }

    //y = g^x mod prime
    public func getY(g: Int, x: Int, p: Int) -> BInt {
        let temp = BInt(g) ** x
        print("Find y; g^x = \(temp), result = y % \(p): \(temp % BInt(p))")
        return temp % BInt(p)
    }

    // 1 < x < prime - 1
    public func getRandomX(p: Int) -> Int {
        var x = 0
        while x == 0 {
            x = Int(arc4random_uniform(UInt32(p - 1)))
            
            if x == 1 {
                x = 0
            }
        }
        print("Random value x: \(x)")

        return x
    }

    //get first primitive root by modulo N
    public func findPrimiteRoot(p: Int) -> Int? {
        for i in 0..<p-1 {
            if primitiveRootModuloN(number: BInt(i), p: p) {
                print("Primitive root modulo \(p) is [g]: \(i)")
                return i
            }
        }
        return nil
    }

    private func primitiveRootModuloN(number: BInt, p: Int) -> Bool {
        if p < 3 || number < 0 {
            return false
        }
        var uniqueMas = Array<BInt>()

        for i in 0..<p-1 {
            let pow: BInt = number ** i
            let result = pow % BInt(p)

            if uniqueMas.contains(result) {
                return false
            } else {
                uniqueMas.append(result)
            }
        }
        return true
    }
}
