package utils;

import java.util.HashMap;
import java.util.Map;

public class StaticUtils {
    
    /**
     * 
     * @return Map of english letter frequencies based on a 40,000 word sample
     * 
     *         data lifted from
     *         http://pi.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html
     */
    public static Map<Character, Double> getFrequencyTable() {
	return Map.ofEntries(Map.entry('e', 12.02), Map.entry('t', 9.1), Map.entry('a', 8.12), Map.entry('o', 7.68),
		Map.entry('i', 7.31), Map.entry('n', 6.95), Map.entry('s', 6.28), Map.entry('r', 6.02),
		Map.entry('h', 5.92), Map.entry('d', 4.32), Map.entry('l', 3.98), Map.entry('u', 2.88),
		Map.entry('c', 2.71), Map.entry('m', 2.61), Map.entry('f', 2.3), Map.entry('y', 2.11),
		Map.entry('w', 2.09), Map.entry('g', 2.03), Map.entry('p', 1.82), Map.entry('b', 1.49),
		Map.entry('v', 1.11), Map.entry('k', 0.69), Map.entry('x', 0.17), Map.entry('q', 0.11),
		Map.entry('j', 0.1), Map.entry('z', 0.07));
    }


    /**
     * takes two equal length buffers and returns the XOR combination
     *
     * @param firstHex
     * @param secondHex
     * @return result of firstHex XOR secondHex
     */
    public static String fixedHexXOR(String firstHex, String secondHex) {
	if (firstHex.length() != secondHex.length()) {
	    throw new IllegalArgumentException();
	}

	StringBuilder result = new StringBuilder();

	int len = firstHex.length();
	for (int i = 0; i < len; i++) {
	    int resultingDigit = hexDigitToInt(firstHex.charAt(i)) ^ hexDigitToInt(secondHex.charAt(i));
	    result.append(intToHexDigit(resultingDigit));
	}

	return result.toString();
    }

    /**
     * takes a hex string and a character(a byte) and returns the XOR combination
     * 
     * @param hex
     *                a hex string
     * @param val
     *                a 1-byte character
     * @return result of hex XOR val
     */
    public static String singleCharXOR(String hex, char val) {
	int len = hex.length();
	if (len % 2 != 0) {
	    hex = "0" + hex;
	}

	StringBuilder sb = new StringBuilder();
	byte[] hexBytes = hexToBytes(hex);
	for (byte b : hexBytes) {
	    sb.append((char) (b ^ (byte) val));
	}

	return sb.toString();
    }

    public static byte[] hexToBytes(String hex) {
	if (hex.length() % 2 == 1)
	    hex = "0" + hex;
	int hexLen = hex.length();
	byte[] hexBytes = new byte[hexLen / 2];
	for (int i = 0; i < hexLen; i += 2) {
	    hexBytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
		    + (Character.digit(hex.charAt(i + 1), 16)));
	}
	return hexBytes;
    }

    public static String bytesToHex(byte[] bytes) {
	StringBuilder sb = new StringBuilder();

	for (byte b : bytes) {
	    int high = (b >> 4) & 0x0F;
	    int low = b & 0x0F;

	    sb.append(intToHexDigit(high));
	    sb.append(intToHexDigit(low));
	}
	return sb.toString();
    }

    public static byte getBit(int num, int pos) {
	return (byte) ((num >> pos) & 1);
    }

    private static char intToHexDigit(int val) {
	if (val < 0 || val > 15) {
	    throw new IllegalArgumentException();
	}

	return "0123456789abcdef".charAt(val);
    }

    private static int hexDigitToInt(char val) {
	if (val >= 'a' && val <= 'f') {
	    return val - 'a' + 10;
	}

	if (val >= 'A' && val <= 'F') {
	    return val - 'A' + 10;
	}

	if (val >= '0' && val <= '9') {
	    return val - '0';
	}

	throw new IllegalArgumentException();
    }

    public static int hammingDistance(String first, String second) {
	byte[] longerLengthBytes;

	if (first.length() > second.length()) {
	    longerLengthBytes = first.getBytes();
	} else {
	    longerLengthBytes = second.getBytes();
	}

	byte[] shorterLengthBytes = longerLengthBytes == first.getBytes() ? second.getBytes() : first.getBytes();

	int count = 0;
	for (int i = 0; i < longerLengthBytes.length; i++) {
	    if (i < shorterLengthBytes.length) {
		byte xorResult = (byte) (longerLengthBytes[i] ^ shorterLengthBytes[i]);
		count += Integer.bitCount(xorResult);
	    } else {
		count += Integer.bitCount(longerLengthBytes[i] ^ (byte) 0);
	    }
	}
	return count;
    }

    public static int hammingDistance(byte[] first, byte[] second) {
	byte[] longerLengthBytes;

	if (first.length > second.length) {
	    longerLengthBytes = first;
	} else {
	    longerLengthBytes = second;
	}

	byte[] shorterLengthBytes = longerLengthBytes == first ? second : first;

	int count = 0;
	for (int i = 0; i < longerLengthBytes.length; i++) {
	    if (i < shorterLengthBytes.length) {
		byte xorResult = (byte) (longerLengthBytes[i] ^ shorterLengthBytes[i]);
		count += Integer.bitCount(xorResult);
	    } else {
		count += Integer.bitCount(longerLengthBytes[i] ^ (byte) 0);
	    }
	}
	return count;
    }

    public static Map<Integer, Character> getBase64IndexTable() {
	Map<Integer, Character> indexTable = new HashMap<>();
	indexTable.put(62, '+');
	indexTable.put(63, '/');
	indexTable.put(64, '=');

	int currentChar = 'A';
	int currentIndex = 0;
	while (indexTable.size() < 65) {
	    indexTable.put(currentIndex++, (char) currentChar++);
	    if (currentChar == 'Z' + 1)
		currentChar = 'a';
	    if (currentChar == 'z' + 1)
		currentChar = '0';
	}
	return indexTable;
    }
    
    public static Map<Character, Double> getLetterFrequencies(String sample) {
	Map<Character, Double> letterFrequencyTableEN = getFrequencyTable();
	
	long numOfLetters = sample.chars().mapToObj(c -> (char) c).filter(c -> letterFrequencyTableEN.containsKey(c)
		|| letterFrequencyTableEN.containsKey(Character.toUpperCase(c))).count();

	Map<Character, Double> letterFrequencies = new HashMap<>();

	int len = sample.length();
	for (int i = 0; i < len; i++) {
	    char current = sample.charAt(i);
	    if (letterFrequencyTableEN.containsKey(current)
		    || letterFrequencyTableEN.containsKey(Character.toUpperCase(current))) {
		letterFrequencies.putIfAbsent(current, 0.0);
		letterFrequencies.put(current, letterFrequencies.get(current) + 1);
	    }
	}

	letterFrequencies.entrySet().stream()
		.forEach(e -> letterFrequencies.put(e.getKey(), (e.getValue() / numOfLetters) * 100.0));

	return letterFrequencies;
	
    }
    
    public static double scoreText(String text) {
	Map<Character, Double> letterFrequencyTableEN = getFrequencyTable();
	Map<Character, Double> letterFrequency = StaticUtils.getLetterFrequencies(text);

	    double currentScore = 0;
	    double maxScore = Integer.MIN_VALUE;
	    for (Map.Entry<Character, Double> e : letterFrequency.entrySet()) {
		char current = e.getKey();
		currentScore += letterFrequencyTableEN.get(current)
			- Math.abs((letterFrequencyTableEN.get(current) - letterFrequency.get(current)));

	    }
	    if (currentScore > maxScore) {
		maxScore = currentScore;
	    }
	return maxScore;
    }
}
