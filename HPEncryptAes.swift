
/*
Created By : Himanshu Prajapati
Github : hrp03
Date : 24th June, 2017
*/

import Foundation
import CryptoSwift

class HPEncryptAes
{
    let iv:String = "0p9o8i7u6y5t4r3e"; // change your vector
    let key:String = "/.,;p]-09ijh654f"; // change your key

    func hpEncrypt(text:String) -> String
    {
        let data = text.data(using: .utf8)!
        let encrypted = try! AES(key: key, iv: iv, blockMode: .CBC).encrypt([UInt8](data))
        let encryptedData = Data(encrypted)
        return encryptedData.base64EncodedString()
    }
    
    func hpDecrypt(cipher:String) -> String
    {
        let data = Data(base64Encoded: cipher)!
        let decrypted = try! AES(key: key, iv: iv, blockMode: .CBC).decrypt([UInt8](data))
        let decryptedData = Data(decrypted)
        return String(bytes: decryptedData.bytes, encoding: .utf8) ?? "Could not decrypt"
    }
    
}

/*

Usage : 

let hpe = HPEncryptAes();
let cipher:String  = hpe.hpEncrypt(text:"Himanshu")        
print("Plain Text : " + hpe.hpDecrypt(cipher: "/YvhXagISQjdrXsSfH3SwQ=="))

*/