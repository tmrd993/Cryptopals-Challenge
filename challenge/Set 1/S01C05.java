package challenge;

import utils.RepeatKeyXOR;

public class S01C05 {

    public static void main(String[] args) {
	String phrase = "Burning 'em, if you ain't quick and nimble\n" + "I go crazy when I hear a cymbal";
	RepeatKeyXOR test = new RepeatKeyXOR(phrase, "ICE");
	System.out.println(test.encodedMessage());
    }

}
