package utils;

import java.util.Map;

/**
 * single-byte XOR cipher solver as described on
 * https://cryptopals.com/sets/1/challenges/3
 *
 * @author Timucin Merdin
 *
 */
public class SBXORSolver {

    private final String HEX_ENCODED_MESSAGE;
    private Map<Character, Double> letterFrequencyTableEN;
    private double score;
    private String plaintext;
    private char key;

    public SBXORSolver(String hexEncodedMessage) {
	HEX_ENCODED_MESSAGE = hexEncodedMessage;
	letterFrequencyTableEN = StaticUtils.getFrequencyTable();
    }

    /**
     * 
     * @return plaintext message after deciphering the single byte XOR cipher
     */
    public String solve() {
	double maxScore = Double.MIN_VALUE;
	String bestMessage = "";
	char key = ' ';
	for (int i = ' '; i <= '~'; i++) {

	    String res = StaticUtils.singleCharXOR(HEX_ENCODED_MESSAGE, (char) i);
	    Map<Character, Double> letterFrequency = StaticUtils.getLetterFrequencies(res);

	    // System.out.println(res);
	    double currentScore = 0;
	    for (Map.Entry<Character, Double> e : letterFrequency.entrySet()) {
		char current = e.getKey();
		currentScore += letterFrequencyTableEN.get(current)
			- Math.abs((letterFrequencyTableEN.get(current) - letterFrequency.get(current)));

	    }
	    if (currentScore > maxScore) {
		maxScore = currentScore;
		bestMessage = res;
		key = (char) i;

	    }

	}
	score = maxScore;
	plaintext = bestMessage;
	this.key = key;
	return "Plaintext: " + plaintext + ", Key: " + key;
    }

    public double score() {
	if (plaintext == null) {
	    solve();
	}
	return score;
    }

    public String plaintext() {
	if (plaintext == null) {
	    solve();
	}
	return plaintext;
    }

    public char key() {
	if (plaintext == null) {
	    solve();
	}
	return key;
    }


}
