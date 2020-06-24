package challenge;

import utils.StaticUtils;

public class S01C02 {

    public static void main(String[] args) {
	String first = "1c0111001f010100061a024b53535009181c";
	String second = "686974207468652062756c6c277320657965";
	
	System.out.println(StaticUtils.fixedHexXOR(first, second).equals("746865206b696420646f6e277420706c6179"));
    }
    
}
