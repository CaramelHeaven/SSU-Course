//
//  main.swift
//  FileFinder
//
//  Created on 05/09/2018.
//  Copyright © 2018 CaramelHeaven. All rights reserved.
//

import Foundation

func getContentDocuments(path: String) -> [String]? {
    guard let paths = try? FileManager.default.contentsOfDirectory(atPath: path)
        else { return nil }
    return paths.map { aContent in (path as NSString).appendingPathComponent(aContent)
    }
}

func getMyFolder() -> String {
    var mas: [String]? = nil
    if let documentsPathString = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first {
        mas = getContentDocuments(path: documentsPathString)
    }
    return mas![5]
}

func getBinary(text: String?) -> String {
    let binaryData: Data? = text!.data(using: .utf8, allowLossyConversion: false)
    let binaryString = binaryData!.reduce("") { (acc, byte) -> String in
        acc + String(byte, radix: 2)
    }
    return binaryString
}

func getRandom_16_bytes(file: String) -> String {
    let absolutePath = getMyFolder() + "/" + file
    var strFromFile = try! String(contentsOf: URL(fileURLWithPath: absolutePath), encoding: .utf8)

    let startPoint = arc4random_uniform(UInt32((strFromFile.count) - 16))
    let lowerBound = strFromFile.index(strFromFile.startIndex, offsetBy: Int(startPoint))
    let upperBound = strFromFile.index(strFromFile.startIndex, offsetBy: Int(startPoint + 16))

    strFromFile = String(strFromFile[lowerBound..<upperBound])
    print("Random chars: \(strFromFile), count: \(strFromFile.count)")
    return String(getBinary(text: strFromFile))
}

func searchFiles(temp: String) -> String {
    let fileManager = FileManager.default
    let emp = fileManager.containerURL(forSecurityApplicationGroupIdentifier: temp)
    return emp!.absoluteString
}

print("Enter the path of file, milord: ")
let mainFile = "England/London/test45.txt"

let miracleBinary = getRandom_16_bytes(file: mainFile)

if NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first != nil {
    let foo = try? FileManager.default.subpathsOfDirectory(atPath: getMyFolder())
    var count = 0

    for folder in foo! {
        let pathMainFolder = getMyFolder() + "/" + folder
        var checkDir: ObjCBool = false

        if FileManager.default.fileExists(atPath: pathMainFolder, isDirectory: &checkDir) {
            if !checkDir.boolValue {
                let data = try? NSString(contentsOfFile: pathMainFolder, encoding: String.Encoding.utf8.rawValue)
                if data != nil {
                    if (!pathMainFolder.contains(mainFile)) {
                        let checking = getBinary(text: data as String?)
                        if (checking.contains(miracleBinary)) {
                            print("text: \(String(describing: data!))")
                            print("path: \(pathMainFolder)")
                            count = count + 1
                        }
                    }
                }
            }
        }
    }
    print("count compatible: \(count)")
}
