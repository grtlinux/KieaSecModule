package org.tain.hmac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class HmacMain {

	public static void main(String[] args) {
		try {
			// nonce = "yyyyMMddHHmmssSSS";
			// url = "rest.sentbe.com/getWebviewId";
			// body = "{'message_key': 'message_value'}";  // json
			// message = nonce + url + body;
			
			String key = "12345678901234567890123456789012";  // Secret-Key
			String message = "the message to hash here";

			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));   // Secret-Key

			byte[] hash = hasher.doFinal(message.getBytes());               // message

			// to lowercase hexits
			//DatatypeConverter.printHexBinary(hash);
			System.out.println(">>>>> Hexit [" + DatatypeConverter.printHexBinary(hash) + "]");     // Hexit

			// to base64
			//DatatypeConverter.printBase64Binary(hash);
			System.out.println(">>>>> Base64 [" + DatatypeConverter.printBase64Binary(hash) + "]");  // Base64
		}
		catch (NoSuchAlgorithmException e) {}
		catch (InvalidKeyException e) {}
	}
}
