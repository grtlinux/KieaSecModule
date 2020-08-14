package org.tain.hmac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class HmacMain {

	public static void main(String[] args) {
		try {
			String key = "12345678901234567890123456789012";
			String message = "the message to hash here";

			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));

			byte[] hash = hasher.doFinal(message.getBytes());

			// to lowercase hexits
			//DatatypeConverter.printHexBinary(hash);
			System.out.println(">>>>> " + DatatypeConverter.printHexBinary(hash));

			// to base64
			//DatatypeConverter.printBase64Binary(hash);
			System.out.println(">>>>> " + DatatypeConverter.printBase64Binary(hash));
		}
		catch (NoSuchAlgorithmException e) {}
		catch (InvalidKeyException e) {}
	}
}
