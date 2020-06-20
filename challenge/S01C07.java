package challenge;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import utils.AESDecryptor;
import utils.Base64Decoder;

public class S01C07 {
    
    private final String BASE64INPUT;
    
    public S01C07(File input) {
	BASE64INPUT = getInputString(input);
    }
    
    public byte[] inputBytes() {
	return new Base64Decoder(BASE64INPUT).decode();
    }
    
    private String getInputString(File input) {
	StringBuilder sb = new StringBuilder();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(input));
	    String line = "";
	    while((line = br.readLine()) != null) {
		sb.append(line);
	    }
	    
	    br.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return sb.toString();
    }
    
    public static void main(String[] args) {
	S01C07 test = new S01C07(new File("\\C:\\Users\\Timucin\\Desktop\\cryptopals\\Set 1\\inputS01C07.txt"));
	AESDecryptor decryptor = new AESDecryptor("YELLOW SUBMARINE", test.inputBytes());
	System.out.println(decryptor.decrypt());
	
    }

}
