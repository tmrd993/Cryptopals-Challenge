package utils;

/**
 * repeat XOR encryptor
 * 
 * @author Timucin Merdin
 *
 */
public class RepeatKeyXOR {

    private final String MESSAGE;
    private final String KEY;

    public RepeatKeyXOR(String msg, String key) {
	this.MESSAGE = msg;
	this.KEY = key;
    }

    /**
     * 
     * @return encrypted message as raw bytes
     */
    public byte[] encode() {
	int modulus = KEY.length();
	int msgLen = MESSAGE.length();
	
	byte[] encodedBytes = new byte[msgLen];

	byte[] keyBytes = KEY.getBytes();
	byte[] msgBytes = MESSAGE.getBytes();
	int index = 0;
	for(byte b : msgBytes) {
	    encodedBytes[index] = (byte) (b ^ keyBytes[index % modulus]);
	    index++;
	}
	return encodedBytes;
    }
    
    /**
     * 
     * @return encrypted message as a hex string
     */
    public String encodedMessage() {
	return StaticUtils.bytesToHex(encode());
    }

    public static void main(String[] args) {
	String phrase = "Burning 'em, if you ain't quick and nimble\n" + 
		"I go crazy when I hear a cymbal";
	RepeatKeyXOR test = new RepeatKeyXOR(phrase, "ICE");
	System.out.println(test.encodedMessage());
	
    }

}
