package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Base64Decoder {

    private final String BASE64_ENCODED_MESSAGE;

    public Base64Decoder(String base64EncodedMessage) {
	this.BASE64_ENCODED_MESSAGE = base64EncodedMessage;
    }

    public byte[] decode() {
	int msgLen = BASE64_ENCODED_MESSAGE.length();
	byte[] decodedBytes = new byte[msgLen - (msgLen / 4)];

	Map<Character, Integer> inverseBase64Table = new HashMap<>();
	StaticUtils.getBase64IndexTable().entrySet().stream()
		.forEach(e -> inverseBase64Table.put(e.getValue(), e.getKey()));

	int decodedBytesIndex = 0;
	for (int i = 0; i < msgLen; i += 4) {
	    int firstSextet = inverseBase64Table.get(BASE64_ENCODED_MESSAGE.charAt(i)) << 18;
	    int secondSextet = inverseBase64Table.get(BASE64_ENCODED_MESSAGE.charAt(i + 1)) << 12;
	    int thirdSextet = BASE64_ENCODED_MESSAGE.charAt(i + 2) == '=' ? 0
		    : inverseBase64Table.get(BASE64_ENCODED_MESSAGE.charAt(i + 2)) << 6;
	    int fourthSextet = BASE64_ENCODED_MESSAGE.charAt(i + 3) == '=' ? 0
		    : inverseBase64Table.get(BASE64_ENCODED_MESSAGE.charAt(i + 3));

	    int threeByteGroup = firstSextet + secondSextet + thirdSextet + fourthSextet;

	    // turn 4 sextets into 3 octets
	    byte[] threeByteGroupBits = new byte[24];
	    for (int j = 0; j < 24; j++) {
		threeByteGroupBits[23 - j] = StaticUtils.getBit(threeByteGroup, j);
	    }

	    for (int j = 0; j < threeByteGroupBits.length; j += 8) {
		int exp = 7;
		byte b = 0;

		for (int k = j; k < j + 8; k++) {
		    b += ((byte) Math.pow(2, exp--)) * threeByteGroupBits[k];
		}
		decodedBytes[decodedBytesIndex++] = b;
	    }

	}

	int numOfPadding = BASE64_ENCODED_MESSAGE.charAt(msgLen - 1) == '='
		? (BASE64_ENCODED_MESSAGE.charAt(msgLen - 2) == '=' ? 2 : 1)
		: 0;
		
	return Arrays.copyOfRange(decodedBytes, 0, decodedBytes.length - numOfPadding);
    }

    public static void main(String[] args) {
	Base64Decoder test = new Base64Decoder("TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlz" + 
		"IHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2Yg" + 
		"dGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGlu" + 
		"dWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRo" + 
		"ZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=");
	System.out.println(new String(test.decode()));
    }

}
