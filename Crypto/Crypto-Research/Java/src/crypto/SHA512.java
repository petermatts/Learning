package crypto;

import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class SHA512 {
    public static String hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] arr = md.digest(input.getBytes());
        BigInteger num = new BigInteger(1, arr);
        String hash = num.toString(16);

        while(hash.length() < 32)
            hash = "0" + hash;

        return hash.toUpperCase();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String hash_empty = hash("");
        System.out.println(hash_empty);

        String hash_helloworld = hash("Hello World");
        System.out.println(hash_helloworld);
    }
}
