package utils;

import java.util.Map;

public class HexToBase64 {
    private final String HEX_VAL;
    private String base64Value;

    private Map<Integer, Character> indexTable;

    public HexToBase64(String hex) {
	HEX_VAL = hex;
	indexTable = StaticUtils.getBase64IndexTable();
    }

    public String encode() {
	if (base64Value != null)
	    return base64Value;

	StringBuilder base64val = new StringBuilder();

	byte[] hexBytes = StaticUtils.hexToBytes(HEX_VAL);
	int hexBitLen = hexBytes.length * 8;
	// pad with zeros if neccessary
	while(hexBitLen % 6 != 0) {
	    hexBitLen++;
	}

	byte[] hexBits = getHexBits(hexBitLen, hexBytes);
	for(int i = 0; i < hexBits.length; i += 6) {
	    int b = 0;
	    int exp = 5;
	    for(int j = i; j < i + 6; j++) {
		b += (int) Math.pow(2, exp--) * hexBits[j];
	    }
	    base64val.append(indexTable.get(b));
	}
	base64Value = base64val.toString();

	// add padding character '=' if neccessary
	if(HEX_VAL.length() % 3 != 0) {
	    while(base64Value.length() % 4 != 0)
		base64Value = base64Value + "=";
	}

	return base64Value;
    }

    public String hexValue() {
	return HEX_VAL;
    }


    private static byte[] getHexBits(int len, byte[] hexBytes) {
	byte[] hexBits = new byte[len];
	int index = 0;
	for(byte b : hexBytes) {
	    for(int i = 7; i >= 0; i--) {
		hexBits[index++] = StaticUtils.getBit(b, i);
	    }
	}
	return hexBits;
    }
}
