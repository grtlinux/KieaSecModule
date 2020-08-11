package org.tain.crypto;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoMain {

	public static void main(String[] args) throws Exception {
		init();
		String plainText = "저를 암호화 해주세요.";
		String encrypted = encrypt(plainText);
		String decrypted = decrypt(encrypted);
		
		System.out.println("plainText: " + plainText);
		System.out.println("encrypted: " + encrypted);
		System.out.println("decrypted: " + decrypted);
	}
	/*
		Information
		1. Secret Key
		2. IV(Initialize Vector)
		3. Cipher Mode(CBC/ECB/...)
		4. Padding Mode(PKCS5/PKCS7/...)
	*/
	private static String ALG = "AES/CBC/PKCS5Padding";
	private static String PK = "01234567890123456789012345678901"; // 32bytes
	private static String IV = PK.substring(0, 16); // 16bytes
	
	private static Cipher cipher = null;
	private static SecretKeySpec keySpec = null;
	private static IvParameterSpec ivParamSpec = null;
	
	public static void init() throws Exception {
		cipher = Cipher.getInstance(ALG);
		keySpec = new SecretKeySpec(IV.getBytes(), "AES");
		ivParamSpec = new IvParameterSpec(IV.getBytes());
	}
	
	public static String encrypt(String plainText) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);;
		
		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public static String decrypt(String cipherText) throws Exception {
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);;
		
		byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, "UTF-8");
	}
}

/* Node.js
    const privateKey = "01234567890123456789012345678901"; // 32byte
    const ivKey = privateKey.substring(0, 16); // 16byte
    const chainingMode = "AES-256-CBC";
    const encrypt = (utf8Text: string) => {
        const cipher = crypto.createCipheriv(chainingMode, privateKey, ivKey);
        cipher.setAutoPadding(false);
        let encrypted = cipher.update(pkcs7Pad(utf8Text), undefined, "base64");
        encrypted += cipher.final("base64");
        return encrypted;
    };

    const decrypt = (base64Text: string) => {
        const decipher = crypto.createDecipheriv(chainingMode, privateKey, this._ivKey);
        decipher.setAutoPadding(false);
        let decrypted = decipher.update(base64Text, "base64", "utf8");
        decrypted += decipher.final("utf8");
        return pkcs7Unpad(decrypted);
    };

    const pkcs7 = require("pkcs7");
    
    const pkcs7Pad = (params: string) => {
        const buffer = Buffer.from(params, "utf8");
        const bytes = new Uint8Array(buffer.length);
        let i = buffer.length;
        while (i--) {
            bytes[i] = buffer[i];
        }
        return Buffer.from(pkcs7.pad(bytes) as Uint8Array);
    }
    
    const pkcs7Unpad = (params: string) => {
        const buffer = Buffer.from(params, "utf8");
        const bytes = new Uint8Array(buffer.length);
        let i = buffer.length;
        while (i--) {
            bytes[i] = buffer[i];
        }
        const result = Buffer.from(pkcs7.unpad(bytes) as Uint8Array);
        return result.toString("utf8");
    }
    
    const plainText = "저를 암호화 해주세요";
    const encrypted = encrypt(plainText);
    console.log("PLAIN_TEXT", plainText);
    console.log("ENCRYPTED", encrypted);
    console.log("DECRYPTED", decrypt(encrypted));
*/
