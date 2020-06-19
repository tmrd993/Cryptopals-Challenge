package challenge;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import utils.Base64Decoder;
import utils.StaticUtils;
import utils.RepeatKeyXORSolver;

public class S01C05 {

    private byte[] encryptedData;

    public S01C05(File input) {
	encryptedData = getEncryptedData(input);
    }

    /**
     * 
     * @param input
     *                  input file containing the base64'd data
     * @return byte array representing the encrypted data after base64 decoding the
     *         input file
     */
    private byte[] getEncryptedData(File input) {
	StringBuilder sb = new StringBuilder();

	try {
	    BufferedReader br = new BufferedReader(new FileReader(input));
	    String line = "";
	    while ((line = br.readLine()) != null) {
		sb.append(line);
	    }

	    br.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	Base64Decoder decoder = new Base64Decoder(sb.toString());

	return decoder.decode();
    }

    public static void main(String[] args) {
	S01C05 test = new S01C05(new File("\\C:\\Users\\Timucin\\Desktop\\cryptopals\\Set 1\\inputS01C05.txt"));
	
	RepeatKeyXORSolver solver = new RepeatKeyXORSolver(test.encryptedData);
	
	System.out.println(new String(StaticUtils.hexToBytes(solver.decrypt())));

    }

}
