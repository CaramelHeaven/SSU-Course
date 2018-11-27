import Foundation

let SEPARATED_RULE = "__"

extension Array {
    func toOneLineArray(array: [String]) -> String {
        let stringRepresent = array.joined(separator: "__")
        return stringRepresent
    }

    func encodingFolders(array: [String]) -> String {
        var decodeString = ""
        for st in array {
            let text = st.encodingString()!
            let kek = text + "__"
            decodeString += kek
        }

        decodeString = decodeString.encodingString()!
        return decodeString
    }
}

extension String {
    func decodingString() -> String? {
        guard let data = Data(base64Encoded: self, options: Data.Base64DecodingOptions(rawValue: 0)) else {
            return nil
        }
        return String(data: data as Data, encoding: String.Encoding.utf8)
    }

    func encodingString() -> String? {
        guard let data = self.data(using: String.Encoding.utf8) else {
            return nil
        }
        return data.base64EncodedString(options: Data.Base64EncodingOptions(rawValue: 0))
    }

    func decodingFolders(bigString: String) -> [String] {
        var array = [String]()

        let firstDecoding = bigString.decodingString()!
        let kek = firstDecoding.components(separatedBy: "__")

        for element in kek {
            let decoding = element.decodingString()!
            array.append(decoding)
        }

        return array
    }
}

func createArhive(content: String) {
    let fileManager = FileManager.default
    if let tDocumentDirectory = fileManager.urls(for: .documentDirectory, in: .userDomainMask).first {
        let filePath = tDocumentDirectory.appendingPathComponent("arhive.security")

        if !fileManager.fileExists(atPath: filePath.path) {
            fileManager.createFile(atPath: filePath.path, contents: nil, attributes: nil)

            do {
                try content.write(to: filePath, atomically: false, encoding: .utf8)
            }
            catch {
                print("Error for writing content to file")
            }
        }
    }
}

func createFile(path: String, content: String) {
    let fileManager = FileManager.default
    if fileManager.urls(for: .documentDirectory, in: .userDomainMask).first != nil {

        if !fileManager.fileExists(atPath: path) {
            fileManager.createFile(atPath: path, contents: nil, attributes: nil)

            do {
                try content.write(to: URL(fileURLWithPath: path), atomically: false, encoding: .utf8)
            }
            catch {
                print("Error for writing content to file")
            }
        }
    }
}

func removeFolder(path: String) {
    let fileManager = FileManager.default

    if fileManager.urls(for: .documentDirectory, in: .userDomainMask).first != nil {
        do {
            try fileManager.removeItem(at: URL(fileURLWithPath: path))
        } catch { }
    }
}

func getContentDocuments(path: String) -> [String]? {
    guard let paths = try? FileManager.default.contentsOfDirectory(atPath: path)
        else { return nil }
    return paths.map {
        aContent in (path as NSString).appendingPathComponent(aContent)
    }
}

func goToEncryptDataFromFolder() {
    print("Enter password, sir")
    let password = readLine()

    print("What do u what to encrypt, sir: ")
    for cont in mas!.indices {
        print("file: \(mas![cont]) -- \(cont)")
    }

    let indexMas = readLine()

    var arrayOfFolders = [String]()
    var arrayOfContent = [String]()

    let filePath = mas![Int(indexMas!)!]

    //get all files from folder
    if NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first != nil {
        let foo = try? FileManager.default.subpathsOfDirectory(atPath: filePath)
        for file in foo! {
            let pathMainFolder = filePath + "/" + file
            print("checking path: \(pathMainFolder)")
            var checkDir: ObjCBool = false

            if FileManager.default.fileExists(atPath: pathMainFolder, isDirectory: &checkDir) {
                if !checkDir.boolValue {
                    if !pathMainFolder.contains(".DS_Store") {
                        let path = pathMainFolder.encodingString()!
                        let text = try? String(contentsOfFile: pathMainFolder)

                        arrayOfContent.append(path)
                        arrayOfContent.append(text?.encodingString() ?? "")
                    }
                } else {
                    arrayOfFolders.append(pathMainFolder)
                }
            }
        }
    }

    var text = arrayOfContent.toOneLineArray(array: arrayOfContent)
    let folders = arrayOfFolders.encodingFolders(array: arrayOfFolders)
    text = text.encodingString()!

    let folderAndText = String(folders + SEPARATED_RULE + text).encodingString()!



    let pas = password!.encodingString()!
    var finish = pas + SEPARATED_RULE + folderAndText
    finish = finish.encodingString()!

    createArhive(content: finish)

    removeFolder(path: filePath)
}

//MAIN

//find folder and get it
var mas: [String]? = nil
if let documentsPathString = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true).first {
    mas = getContentDocuments(path: documentsPathString)
}

print("What do u do? Encrypt? Y/N")
let action = readLine()

if action == "Y" {
    goToEncryptDataFromFolder()
} else {
    var baseText = ""

    print("What do u what to decrypt, sir: ")
    for cont in mas!.indices {
        print("file: \(mas![cont]) -- \(cont)")
    }
    let indexMas = readLine()

    let filePath = mas![Int(indexMas!)!]

    if FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first != nil {
        baseText = try String(contentsOf: URL(fileURLWithPath: filePath), encoding: .utf8)
    }

    print("Entered password for decrypting, sir")
    let getPassword = readLine()!

    //get password
    let firstDecrypt = baseText.decodingString()

    var dividedArrayPT = firstDecrypt?.components(separatedBy: SEPARATED_RULE)
    let password = dividedArrayPT![0].decodingString()!

    if getPassword == password {
        let content = dividedArrayPT![1]

        var arrayOfFoldersAndText = content.decodingString()?.components(separatedBy: SEPARATED_RULE)

        let foldersData = arrayOfFoldersAndText![0].decodingString()!

        for folder in foldersData.components(separatedBy: SEPARATED_RULE) {
            let path = folder.decodingString()!

            if path.count > 3 {
                try FileManager.default.createDirectory(at: URL(fileURLWithPath: path), withIntermediateDirectories: true, attributes: nil)
            }
        }

        let filesData = arrayOfFoldersAndText![1].decodingString()
        let arrayFiles = filesData!.components(separatedBy: SEPARATED_RULE)

        for index in stride(from: 0, to: arrayFiles.count, by: 2) {
            let indexContent = index + 1
            createFile(path: arrayFiles[index].decodingString()!, content: arrayFiles[indexContent].decodingString()!)
        }

        removeFolder(path: filePath)
    } else if getPassword != password {
        print("Entered password does not fit")
    } else {
        print("u entered incompatible rule")
    }
}
print("Completed")
