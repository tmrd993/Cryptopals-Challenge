package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 128-bit AES with ECB decryptor
 * 
 * @author Timucin Merdin
 */
public class AESDecryptor {

    private final String KEY;
    private final byte[] encryptedData;

    public AESDecryptor(String key, String encryptedData) {
	this.KEY = key;
	this.encryptedData = encryptedData.getBytes();
    }

    public AESDecryptor(String key, byte[] encryptedData) {
	this.KEY = key;
	this.encryptedData = encryptedData;
    }

    public String decrypt() {
	try {
	    Cipher cipher = Cipher.getInstance("AES/ECB/NOPADDING");
	    SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    return new String(cipher.doFinal(encryptedData));

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

}
