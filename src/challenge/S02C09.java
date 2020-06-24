package challenge;

import java.util.Arrays;

import utils.StaticUtils;

public class S02C09 {

    public static void main(String[] args) {
	String key = "YELLOW SUBMARINE";
	byte[] paddedKey = StaticUtils.pkcs7(key.getBytes(), 20);
	System.out.println(Arrays.toString(paddedKey));
    }
    
}
