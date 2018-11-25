import Foundation

func getContentDocuments(path: String) -> [String]? {
    guard let paths = try? FileManager.default.contentsOfDirectory(atPath: path)
        else { return nil }
    return paths.map { aContent in (path as NSString).appendingPathComponent(aContent)
    }
}

print("Hello, World!")
let FOLDER_NAME = "test2.zi"

//CREATE FILE
let fileManager = FileManager.default
if let tDocumentDirectory = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first {
    let filePath = tDocumentDirectory.appendingPathComponent("\(FOLDER_NAME)")

    if !fileManager.fileExists(atPath: filePath.path) {
        fileManager.createFile(atPath: filePath.path, contents: nil, attributes: nil)
    }
}

//find folder and get it
var mas: [String]? = nil
if let documentsPathString = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first {
    mas = getContentDocuments(path: documentsPathString)
    print(mas!)
}

//get all files from folder
if NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first != nil {
    let foo = try? FileManager.default.subpathsOfDirectory(atPath: mas![3])
    for file in foo! {
        let pathMainFolder = "/Users//Documents/testDirectory/" + file
        var checkDir: ObjCBool = false

        if FileManager.default.fileExists(atPath: pathMainFolder, isDirectory: &checkDir) {
            if !checkDir.boolValue {
                if !pathMainFolder.contains(".DS_Store") {
                    var data = NSData(contentsOfFile: pathMainFolder)
                    let array = [UInt8](data as! Data)
                    print(array)
                    //var allText = try? String(contentsOfFile: pathMainFolder)
                    print("put: \(pathMainFolder)")
                    //print(allText!)
                }
            } else {
                print("folder: \(pathMainFolder)")
            }
        }
    }
    print(foo!)
}
