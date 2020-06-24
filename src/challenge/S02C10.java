package challenge;

import java.io.UnsupportedEncodingException;

import java.util.Base64;

public class S02C10 {
    
    public static void main(String[] args) throws UnsupportedEncodingException {
	String message = "this is my own message";
	
	System.out.println("Before encryption: " + new String(Base64.getEncoder().encode(message.getBytes())));
 	
	
    }

}
