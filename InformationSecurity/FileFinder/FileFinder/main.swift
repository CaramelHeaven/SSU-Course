//
//  main.swift
//  FileFinder
//
//  Created on 05/09/2018.
//  Copyright © 2018 CaramelHeaven. All rights reserved.
//

import Foundation

print("Hello, World!")

func getContentDocuments(path: String) -> [String]? {
    guard let paths = try? FileManager.default.contentsOfDirectory(atPath: path)
        else { return nil }
    return paths.map { aContent in (path as NSString).appendingPathComponent(aContent)
    }
}

func searchFiles(temp: String) -> String {
    let fileManager = FileManager.default
    var emp = fileManager.containerURL(forSecurityApplicationGroupIdentifier: temp)
    return emp!.absoluteString
}

if let documentsPathString = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first {
    let myContent = getContentDocuments(path: documentsPathString)
    let foo = try? FileManager.default.subpathsOfDirectory(atPath: myContent![5])
    for folder in foo! {
        var pathMainFolder = myContent![5] + "/" + folder
        var checkDir: ObjCBool = false





        if FileManager.default.fileExists(atPath: pathMainFolder, isDirectory: &checkDir) {
            if checkDir.boolValue {
                print("true")
                let deeplyContent = try? FileManager.default.contentsOfDirectory(atPath: pathMainFolder)
                for deeply in deeplyContent! {
                    print(deeply)
                    var fk = pathMainFolder + "/" + deeply + "/"
                    print(fk)
                    var tempBool: ObjCBool = false
                    if FileManager.default.fileExists(atPath: fk, isDirectory: &tempBool) {
                        if tempBool.boolValue {
                            print("exixst")
                        } else {
                            print("non exists")
                        }
                    }
                }
            }
            else {
                var data = try? NSString(contentsOfFile: pathMainFolder, encoding: String.Encoding.utf8.rawValue)
                if data == nil {
                    print("nothing")
                } else {
                    if (data?.contains("kel"))! {
                        print("ok")
                    }
                    print("data \(data!)")
                }
            }
        }

    }
}

func searchData(rootPath: String) {

}





