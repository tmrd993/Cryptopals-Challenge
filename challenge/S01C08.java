package challenge;

import java.util.List;
import java.util.Set;

import utils.StaticUtils;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import utils.AESDecryptor;

public class S01C08 {

    private List<String> hexCipherTexts;

    public S01C08(File input) {
	hexCipherTexts = getCipherTexts(input);
    }

    private List<String> getCipherTexts(File input) {
	List<String> cipherTexts = new ArrayList<>();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(input));
	    String line = "";
	    while ((line = br.readLine()) != null) {
		cipherTexts.add(line);
	    }
	    br.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return cipherTexts;
    }

    public String findEncodedText() {
	List<String> possibleHits = possibleCiphers();
	
	return possibleHits.size() > 0 ? possibleHits.get(0) : null;
    }

    /**
     * attacks 16 byte of data at a time with a random key to find out how many blocks decrypt to the same plaintext
     * 
     * @return a list of hex encoded strings that (possibly) have been AES encrypted with ECB mode
     */
    private List<String> possibleCiphers() {
	List<String> possibleHits = new ArrayList<>();
	for (String hexCipher : hexCipherTexts) {
	    byte[] dataBlocks = StaticUtils.hexToBytes(hexCipher);
	    int fullBlockLength = dataBlocks.length;
	    int blockLength = 16;
	    // 16-byte key, randomly chosen. It doesn't matter as long as the same key
	    // is used to decrypt all ciphers
	    String key = "1234567898765431";
	    Set<String> decryptedBlocks = new HashSet<>();
	    for (int i = 0; i < fullBlockLength - blockLength; i += blockLength) {
		AESDecryptor solver = new AESDecryptor(key, Arrays.copyOfRange(dataBlocks, i, i + blockLength));
		String plaintext = solver.decrypt();
		if (decryptedBlocks.contains(plaintext)) {
		    if(!possibleHits.contains(hexCipher)) {
			possibleHits.add(hexCipher);
		    }
		}
		decryptedBlocks.add(plaintext);
	    }
	}
	return possibleHits;
    }

    public static void main(String[] args) {
	S01C08 test = new S01C08(new File("\\C:\\Users\\Timucin\\Desktop\\cryptopals\\Set 1\\inputS01C08.txt"));
	System.out.println(test.findEncodedText());
    }

}
