//
//  main.swift
//  FileFinder
//
//  Created on 05/09/2018.
//  Copyright © 2018 CaramelHeaven. All rights reserved.
//
//
//

import Foundation

//find subarray into array.
extension Array where Element: Equatable {
    func contains(subarray: [Element]) -> Bool {
        var found = 0
        for element in self where found < subarray.count {
            if element == subarray[found] {
                found += 1
            } else {
                found = element == subarray[0] ? 1 : 0
            }
        }
        return found == subarray.count
    }
}

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

func getBytesAndRandomingIt(file: String, boolTake: Bool) -> [String] {
    let absolutePath = file
    
    var binaryString: [String] = []
    if let stream: InputStream = InputStream(fileAtPath: absolutePath) {
        var buf = [UInt8](repeating: 0, count: 16)
        
        stream.open()
        while true {
            let length = stream.read(&buf, maxLength: buf.count)
            for i in 0..<length {
                binaryString.append(String(buf[i]))
            }
            if length < buf.count {
                break
            }
        }
        stream.close()
    }
    if boolTake {
        let binary = getRandom(massive: binaryString)
        return binary
    } else {
        return binaryString
    }
}

func getRandom(massive: [String]) -> [String] {
    let randomNumbers = arc4random_uniform(UInt32((massive.count) - 16))
    var bytes: [String] = []
    for temp in randomNumbers...randomNumbers + 16 {
        bytes.append(massive[Int(temp)])
    }
    return bytes
}

print("Put here reference from your file, sir.")

let mainFile = readLine()
let mainBytes = getBytesAndRandomingIt(file: mainFile!, boolTake: true)

print("base bytes: \(mainBytes)")
if NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first != nil {
    let foo = try? FileManager.default.subpathsOfDirectory(atPath: getMyFolder())
    var countCompatible = 0
    var uncompatible = 0
    
    for folder in foo! {
        let pathMainFolder = getMyFolder() + "/" + folder
        var checkDir: ObjCBool = false
        
        if FileManager.default.fileExists(atPath: pathMainFolder, isDirectory: &checkDir) {
            if !checkDir.boolValue {
                if !pathMainFolder.contains(".DS_Store") {
                    let temp = getBytesAndRandomingIt(file: pathMainFolder, boolTake: false)
                    
                    
                    if (temp.contains(subarray: mainBytes)) {
                        print("path container: \(pathMainFolder)")
                        print("bytes: \(temp)")
                        countCompatible += 1;
                    } else {
                        uncompatible += 1
                    }
                }
            }
        }
    }
    print("Conclusion: count compatible = \(countCompatible), uncompatible = \(uncompatible)")
}
