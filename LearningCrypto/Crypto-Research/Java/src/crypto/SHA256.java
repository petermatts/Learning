package crypto;

// import java.math.BigInteger; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List; 

public class SHA256 {
    /* Manual SHA256 calculation */
    /* Implemented via the instructions @ https://qvault.io/cryptography/how-sha-2-works-step-by-step-sha-256/ */

     /** 
     * The intial has values that the SHA256 cryptographic hashing algorithm uses
     * The array consists of the fratcional parts of square root of the first 8 primes times 2^32 in hexadecimal
     * 
     * It is equal to the following { (sqrt(2)-1)*2^32, (sqrt(3)-1)*2^32, (sqrt(5)-2)*2^32, (sqrt(7)-2)*2^32, (sqrt(11)-3)*2^32, (sqrt(13)-3)*2^32, (sqrt(17)-4)*2^32, (sqrt(19)-4)*2^32 }
     * 
     * This array is commonly refered to as (h) in pseduocode
     */
    private static final Integer[] INTIAL_HASHES = { 0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19 };

    /**
     * These are rounding constants that the SHA 256 uses to compute the final hash value
     * 
     * Each element in the array is equal to the fractional piece of the cube root of the corresponding first 64 prime numbers times 2^32 in hexadecimal
     * 
     * The array would be equal to the following { (cbrt(2)-1)*2^32, (cbrt(3)-1)*2^32, (cbrt(5)-1)*2^32, ... , (cbrt(311)-6)*2^32 }
     * 
     * This array is commonly refered to as (k) in pseduocode
     */
    private static final Integer[] ROUND_CONSTANTS = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
        0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
        0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
        0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
        0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
        0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
        0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2 
    };

    /* Helper Methods */

    private static String padTo32Bits(String bin) {
        String result = bin;
        while(result.length() < 32)
            result = "0" + result;
        return result;
    }
    
    /**
     * Pads a string to the intended length with leading zeros
     * @param str
     * @param len
     * @return
     */
    private static String padWith0s(String str, int len) {
        String result = str;
        while(result.length() < len)
            result = "0" + result;
        return result;
    }


    /**
     * Adds all binary strings entered together module 2^32
     * @param bin varargs parmeter of binary strings
     * @return binary String of sum of input
     */
    private static String add(String... bin) {
        final long mod = (long) Math.pow(2, 32);
        long sum = 0;

        for(String b : bin) {
            Long add = Long.parseLong(b, 2);
            sum += add;
            sum %= mod;
        }

        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        return result;
    }

    private static String shiftRight(String bin, int distance) {
        final int len = bin.length();
        Long num = Long.parseLong(bin, 2);
        num = num>>distance;
        String result = Long.toBinaryString(num);
        while(result.length() < len)
            result = "0" + result;

        // System.out.println(result.length()); //Should always be 32 for SHA256
        // System.out.println(num); //base 10 representation

        return result;
    }

    private static String rotateRight(String bin, int distance) {
        distance %= bin.length();
        String left = bin.substring(0, bin.length() - distance);
        String right = bin.substring(bin.length() - distance);
        // System.out.println(Long.parseLong(right+left, 2)); //base 10 representation

        return right + left;
    }

    private static String choice(String bin1, String bin2, String bin3) {
        Long long1 = Long.parseLong(bin1, 2);
        Long long2 = Long.parseLong(bin2, 2);
        Long long3 = Long.parseLong(bin3, 2);
        Long sum = (long1 & long2) ^ (~long1 & long3);

        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        // System.out.println(result);

        return result;
    }

    private static String majority(String bin1, String bin2, String bin3) {
        Long long1 = Long.parseLong(bin1, 2);
        Long long2 = Long.parseLong(bin2, 2);
        Long long3 = Long.parseLong(bin3, 2);
        Long sum = (long1 & long2) ^ (long1 & long3) ^ (long2 & long3);

        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        // System.out.println(result);

        return result;
    }

    private static String sigma0(String bin) {
        Long long1 = Long.parseLong(rotateRight(bin, 7), 2);
        Long long2 = Long.parseLong(rotateRight(bin, 18), 2);
        Long long3 = Long.parseLong(shiftRight(bin, 3), 2);

        Long sum = long1 ^ long2 ^ long3;
        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        // System.out.println(result);

        return result;
    }

    private static String sigma1(String bin) {
        Long long1 = Long.parseLong(rotateRight(bin, 17), 2);
        Long long2 = Long.parseLong(rotateRight(bin, 19), 2);
        Long long3 = Long.parseLong(shiftRight(bin, 10), 2);

        Long sum = long1 ^ long2 ^ long3;
        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        // System.out.println(result);

        return result;
    }

    private static String SIGMA0(String bin) {
        Long long1 = Long.parseLong(rotateRight(bin, 2), 2);
        Long long2 = Long.parseLong(rotateRight(bin, 13), 2);
        Long long3 = Long.parseLong(rotateRight(bin, 22), 2);
        Long sum = long1 ^ long2 ^ long3;
        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        // System.out.println(result);

        return result;
    }

    private static String SIGMA1(String bin) { 
        Long long1 = Long.parseLong(rotateRight(bin, 6), 2);
        Long long2 = Long.parseLong(rotateRight(bin, 11), 2);
        Long long3 = Long.parseLong(rotateRight(bin, 25), 2);
        Long sum = long1 ^ long2 ^ long3;
        String result = Long.toBinaryString(sum);
        while(result.length() < 32)
            result = "0" + result;

        // System.out.println(result);

        return result;
    }

    /**
     * Converts an Byte to a string of 8 bits (padding with leading zeroes if necessary)
     * @param Byte
     * @return a binary String of 1's and 0's of length 8 
     */
    private static String toBinaryString(Byte b) {
        String result = Integer.toBinaryString(Byte.toUnsignedInt(b));
        while(result.length() < 8)
            result = "0" + result;

        return result;
    }

    /* End of helper methods */

    /**
     * Computes the SHA256 hash of any given input string
     * @param input string to be hashed
     * @return 64 character String 
     */
    public static String hash(String input) throws Exception {
        /** 
         * Set-up
         * 
         * In this section the input is converted into a binary string and chunked into blocks of 512 bits
         * 
        */

        /* Main Variable Declaration */
        byte[] bytes = input.getBytes();
        List<String> messageBlocks = new ArrayList<String>();
        final Integer BIG_ENDIAN = input.length()*8;
        String strBE = Integer.toBinaryString(BIG_ENDIAN);
        while(strBE.length() < 64)
            strBE = "0" + strBE;

        /* Chunk data into 512 bit blocks (Strings of length 512) */
        String block = "";
        for(int i = 0; i < bytes.length; i++) {
            String add = toBinaryString(bytes[i]);
            block += add;

            if(block.length() == 512) {
                messageBlocks.add(block);
                block = "";
            }
        }

        if(!block.isEmpty())
            messageBlocks.add(block);

        /* Finalize data alteration for next steps */

        //append a 1 at the very end of the last message block
        String last = "";
        if(!messageBlocks.isEmpty())
            last = messageBlocks.remove(messageBlocks.size()-1);

        last += "1";

        //pad with trailing zeroes until last is of length divisble by 512, but stop 64 spots shy to insert strBE (which always has length 64)
        while((last.length()+64)%512 != 0)
            last += "0";

        //insert steBE to the end of last
        last += strBE;
        
        //reinsert into message Blocks
        if(last.length() <= 512) {
            messageBlocks.add(last);
        } else {
            for(int lower = 0; lower < last.length(); lower+=512) {
                int upper = lower + 512;
                if(upper < last.length()) {
                    messageBlocks.add(last.substring(lower));
                } else {
                    messageBlocks.add(last.substring(lower, upper));
                }
            }
        }

        /**
         * The Good Stuff
         * 
         * In this section the inital hash values will be initialized
         * and a message schedule will be created from the chunks in 
         * messageBlocks to calculate new values
         * 
         * The word list/array refered to as (w) commonly in pseduocode will 
         * be used and calculated using bitwise function
         */

        /* Initialize constants and intial hash values */
        String[] H = new String[INTIAL_HASHES.length];
        for(int i = 0; i < H.length; i++)
            H[i] = padTo32Bits(Integer.toBinaryString(INTIAL_HASHES[i]));

        Integer[] K = ROUND_CONSTANTS;

        /* Looping through chunks */
        String[] w = new String[64];
        for(int chunk = 0; chunk < messageBlocks.size(); chunk++) {
            String word = messageBlocks.get(chunk);
            w[0] = word.substring(0, 32);
            w[1] = word.substring(32, 64);
            w[2] = word.substring(64, 96);
            w[3] = word.substring(96, 128);
            w[4] = word.substring(128, 160);
            w[5] = word.substring(160, 192);
            w[6] = word.substring(192, 224);
            w[7] = word.substring(224, 256);
            w[8] = word.substring(256, 288);
            w[9] = word.substring(288, 320);
            w[10] = word.substring(320, 352);
            w[11] = word.substring(352, 384);
            w[12] = word.substring(384, 416);
            w[13] = word.substring(416, 448);
            w[14] = word.substring(448, 480);
            w[15] = word.substring(480, 512); // word.substring(480); works too

            //For w[16 ... 63]
            //w[i] = w[i-16] + sigma0(w[i-15]) + w[i-7] + sigma1(w[i-2])
            for(int i = 16; i < w.length; i++)
                w[i] = add(w[i-16], sigma0(w[i-15]), w[i-7], sigma1(w[i-2]));

            //print out message schedule
            // for(int i = 0; i < w.length; i++)
            //     System.out.println(w[i]);

            //Initialize compression variables
            String a = H[0];
            String b = H[1];
            String c = H[2];
            String d = H[3];
            String e = H[4];
            String f = H[5];
            String g = H[6];
            String h = H[7];

            // System.out.println(a);
            // System.out.println(b);
            // System.out.println(c);
            // System.out.println(d);
            // System.out.println(e);
            // System.out.println(f);
            // System.out.println(g);
            // System.out.println(h);

            /* Compression Loop */
            for(int i = 0; i < 64; i++) {
                //temp1 = h + SIGMA1(e) + choice(e, f, g) + k[i] + w[i]
                String temp1 = add(h, SIGMA1(e), choice(e, f, g), Integer.toBinaryString(K[i]), w[i]);
                
                //temp2 = SIMGA0(a) + majority(a, b, c)
                String temp2 = add(SIGMA0(a), majority(a, b, c));

                // if(i==0) {
                //     System.out.println("temp1:");
                //     System.out.println("01011011110111010101100111010100\tExpected");
                //     System.out.println(temp1+"\tActual");

                //     System.out.println("temp2:");
                //     System.out.println("00001000100100001001101011100101\tExpected");
                //     System.out.println(temp2+"\tActual");
                // }

                //nitty gritty, shifting variables
                h = g;
                g = f;
                f = e;
                e = add(d, temp1);
                d = c;
                c = b;
                b = a;
                a = add(temp1, temp2);

                // if(i==0) {
                //     System.out.println("a " + a);
                //     System.out.println("b " + b);
                //     System.out.println("c " + c);
                //     System.out.println("d " + d);
                //     System.out.println("e " + e);
                //     System.out.println("f " + f);
                //     System.out.println("g " + g);
                //     System.out.println("h " + h);
                // }
            }

            // System.out.println(b);

            H[0] = add(H[0], a);
            H[1] = add(H[1], b);
            H[2] = add(H[2], c);
            H[3] = add(H[3], d);
            H[4] = add(H[4], e);
            H[5] = add(H[5], f);
            H[6] = add(H[6], g);
            H[7] = add(H[7], h);
        }

        Long l0 = Long.parseLong(H[0], 2);
        Long l1 = Long.parseLong(H[1], 2);
        Long l2 = Long.parseLong(H[2], 2);
        Long l3 = Long.parseLong(H[3], 2);
        Long l4 = Long.parseLong(H[4], 2);
        Long l5 = Long.parseLong(H[5], 2);
        Long l6 = Long.parseLong(H[6], 2);
        Long l7 = Long.parseLong(H[7], 2);
        String hash = padWith0s(Long.toHexString(l0), 8)
                      + padWith0s(Long.toHexString(l1), 8)
                      + padWith0s(Long.toHexString(l2), 8)
                      + padWith0s(Long.toHexString(l3), 8)
                      + padWith0s(Long.toHexString(l4), 8)
                      + padWith0s(Long.toHexString(l5), 8)
                      + padWith0s(Long.toHexString(l6), 8)
                      + padWith0s(Long.toHexString(l7), 8);

        /* And finally, bundling and the return statement! */
        return hash.toUpperCase();
    }

    /***** End Manual Section *****/


    /***** Java API SHA256 *****/

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException { 
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    }
    
    private static String toHexString(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    } 

    public static String hashAPI(String input) throws NoSuchAlgorithmException, Exception {
        String hash = toHexString(getSHA(input)).toUpperCase();

        if(hash.length() != 64) {
            throw new Exception("hash length != 64, length=" + hash.length() + "\nInput: " + input);
        }

        return hash;
    }

    /***** End Jave API section *****/

    public static void main(String args[]) throws Exception { 
        // String hash = hash("hello world");
        // System.out.println("hello world");
        // System.out.println(hash + "\t(Result)");
        // System.out.println("B94D27B9934D3E08A52E52D7DA7DABFAC484EFE37A5380EE9088F7ACE2EFCDE9\t(Expected)");

        // System.out.println("\nHello World");
        // System.out.println(hash("Hello World") + "\t(Result)");
        // System.out.println("A591A6D40BF420404A011733CFB7B190D62C65BF0BCDA32B57B277D9AD9F146E\t(Expected)");

        // String testBoth = ""; //adjust this string value to any value to test both methods of calculating the hash
        // System.out.println("\nTest Both:");
        // System.out.println(hashAPI(testBoth));
        // System.out.println(hash(testBoth));

        String name = "Ryan Peters";
        System.out.println(name + ": " + hashAPI(name));
    } 
}
