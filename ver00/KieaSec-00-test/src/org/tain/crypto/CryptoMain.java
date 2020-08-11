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
