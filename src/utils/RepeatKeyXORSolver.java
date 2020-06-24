package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RepeatKeyXORSolver {

    private byte[] encryptedData;

    public RepeatKeyXORSolver(String encryptedMessage) {
	encryptedData = encryptedMessage.getBytes();
    }

    public RepeatKeyXORSolver(byte[] encryptedData) {
	this.encryptedData = encryptedData;
    }

    /**
     * 
     * @return encryption key for the repeating XOR cipher
     */
    private String getKey() {
	int[] possibleKeyLengths = IntStream.range(2, 41).toArray();
	// list of pairs storing the normalized average hamming distances for the key
	// lengths
	List<Pair<Integer, Double>> nAvgHDforKeyLengths = nAvgHD(possibleKeyLengths);
	nAvgHDforKeyLengths.sort((k1, k2) -> k1.value().compareTo(k2.value()));

	// get 3 possible key lengths with the lowest average normalized HD
	List<Integer> keyLengths = nAvgHDforKeyLengths.subList(0, 1).stream().map(k -> k.key())
		.collect(Collectors.toList());
	List<String> possibleKeys = new ArrayList<>();
	for (Integer keyLength : keyLengths) {
	    byte[] transposedBlocks = transpose(encryptedData, keyLength);
	    StringBuilder sb = new StringBuilder();

	    int blockLength = transposedBlocks.length / keyLength;
	    for (int i = 0; i < transposedBlocks.length - blockLength; i += blockLength) {
		SBXORSolver solver = new SBXORSolver(StaticUtils.bytesToHex(
			Arrays.copyOfRange(transposedBlocks, i, i + (transposedBlocks.length / keyLength))));
		sb.append(solver.key());
	    }
	    possibleKeys.add(sb.toString());
	}

	double maxScore = Integer.MIN_VALUE;
	String bestKey = null;
	for (String key : possibleKeys) {
	    double score = StaticUtils.scoreText(key);
	    if (score > maxScore) {
		maxScore = score;
		bestKey = key;
	    }
	}

	return bestKey;
    }

    public String decrypt() {
	String key = getKey();
	String data = new String(encryptedData);
	RepeatKeyXOR solver = new RepeatKeyXOR(data, key);

	return solver.encodedMessage();
    }

    private byte[] transpose(byte[] data, int keyLength) {
	byte[] transposedBlocks = new byte[data.length];
	int transposedBlockIndex = 0;

	for (int i = 0; i < keyLength; i++) {
	    for (int j = i; j < data.length; j += keyLength) {
		transposedBlocks[transposedBlockIndex++] = data[j];
	    }
	}
	return transposedBlocks;
    }

    /**
     * 
     * @param possibleKeyLengths
     *                               guessed keysizes (2 - 40 in this case)
     * @return a list of keysizes and corresponding normalized average hamming
     *         distances
     */
    private List<Pair<Integer, Double>> nAvgHD(int[] possibleKeyLengths) {
	List<Pair<Integer, Double>> nAvgHDforKeyLengths = new ArrayList<Pair<Integer, Double>>();
	int dataLen = encryptedData.length;
	for (int keyLength : possibleKeyLengths) {
	    double avgHD = 0;
	    int groupCount = 0;
	    for (int i = 0; i < dataLen - keyLength; i += keyLength) {
		byte[] firstGroup = Arrays.copyOfRange(encryptedData, i, i + keyLength);
		byte[] secondGroup = Arrays.copyOfRange(encryptedData, i + keyLength, i + (2 * keyLength));
		avgHD += StaticUtils.hammingDistance(firstGroup, secondGroup);
		groupCount++;
	    }
	    avgHD /= groupCount;
	    double nAvgHD = avgHD / keyLength;
	    nAvgHDforKeyLengths.add(new Pair<Integer, Double>(keyLength, nAvgHD));
	}

	return nAvgHDforKeyLengths;
    }

}
