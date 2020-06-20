package challenge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

import utils.SBXORSolver;

public class S01C04 {
    
    List<String> inputStrings;
    
    public S01C04(File input) {
	inputStrings = getInputStrings(input);
    }
    
    public String solve() {
	List<SBXORSolver> results = new ArrayList<>();
	
	for(String inputString : inputStrings) {
	    SBXORSolver solver = new SBXORSolver(inputString);
	    results.add(solver);
	}
	return results.stream().max(Comparator.comparing(x -> x.score())).get().solve();
    }
    
    private List<String> getInputStrings(File input) {
	List<String> inputStrings = new ArrayList<>();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(input));
	    String inputString = "";
	    
	    while((inputString = br.readLine()) != null) {
		inputStrings.add(inputString);
	    }
	    br.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return inputStrings;
    }
    
    public static void main(String[] args) {
	S01C04 test = new S01C04(new File("C:\\Users\\Timucin\\Desktop\\cryptopals\\Set 1\\inputS01C04.txt"));
	System.out.println(test.solve());
    }

}
