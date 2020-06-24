package utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESECB {

 
    public String encrypt(String message, String key) {
	
	try {
	    Cipher cipher = Cipher.getInstance("AES/ECB/NOPADDING");
	    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    byte[] encryptedMessage = cipher.doFinal(message.getBytes("UTF-8"));
	    return Base64.getEncoder().encodeToString(encryptedMessage);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	} catch (NoSuchPaddingException e) {
	    e.printStackTrace();
	} catch (InvalidKeyException e) {
	    e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
	    e.printStackTrace();
	} catch (BadPaddingException e) {
	    e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}

	return null;
    }
    
    public String decrypt(String message, String key) {
	try {
	    Cipher cipher = Cipher.getInstance("AES/ECB/NOPADDING");
	    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    String result =  new String(cipher.doFinal(Base64.getDecoder().decode(message.getBytes("UTF-8"))));
	    return result;
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	} catch (NoSuchPaddingException e) {
	    e.printStackTrace();
	} catch (InvalidKeyException e) {
	    e.printStackTrace();
	} catch (IllegalBlockSizeException e) {
	    e.printStackTrace();
	} catch (BadPaddingException e) {
	    e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    public static void main(String[] args) {
	AESECB test = new AESECB();
	String encrypted = test.encrypt("this is a test message", "YELLOW SUBMARINE");
	System.out.println(encrypted);
	System.out.println(test.decrypt(encrypted, "YELLOW SUBMARINE"));
    }

}
