package challenge;

import utils.HexToBase64;

public class S01C01 {

    public static void main(String[] args) {
	HexToBase64 test = new HexToBase64(
		"49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d");

	System.out.println(test.encode().equals("SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"));
    }

}
