package crypto;

/**
 * This class was made for me to work around and  test with bitwise operators for the first time :)
 */
public class BitwiseString {
    /**
     * Adds all binary strings entered together module 2^32
     * Pads with zeroes to 32 bits (if necessary)
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

    /**
     * Shifts all bits to the left by the specified distances, pads with zeroes to 32 bits if necessary
     * @param bin binary string input, cannot exceed Long.MAX_VALUE
     * @param distance bit distance to shift
     * @return the left shifted binary string
     */
    private static String shiftLeft(String bin, int distance) {
        final int len = bin.length();
        Long num = Long.parseLong(bin, 2);
        num = num<<distance;
        String result = Long.toBinaryString(num);
        while(result.length() < len)
            result = "0" + result;

        // System.out.println(result.length()); //Should always be 32 for SHA256
        // System.out.println(num); //base 10 representation

        return result;
    }


    /**
     * Shifts all bits to the right by the specified distances, pads with zeroes to 32 bits if necessary
     * @param bin binary string input, cannot exceed Long.MAX_VALUE
     * @param distance bit distance to shift
     * @return the right shifted binary string
     */
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

    /**
     * Rotates bits in a binary string to the left, bits shifted over 
     * left boundary return in order on the right of the string.
     * 
     * @param bin binary string to be rotated
     * @param distance to rotate binary string
     * @return left rotated binary string
     */
    private static String rotateLeft(String bin, int distance) {
        distance %= bin.length();
        String left = bin.substring(0, distance);
        String right = bin.substring(distance);
        // System.out.println(Long.parseLong(right+left, 2)); //base 10 representation

        return right + left;
    }

    /**
     * Rotates bits in a binary string to the right, bits shifted over 
     * right boundary return in order on the left of the string.
     * 
     * @param bin binary string to be rotated
     * @param distance to rotate binary string
     * @return right rotated binary string
     */
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

    /**
     * At each bit index in the binary strings, a new binary string contains the
     * bit that has majority at this location between bin1, bin2, & bin3
     * 
     * @param bin1 binary string
     * @param bin2 binary string
     * @param bin3 binary string
     * @return binary string padded to 32 bits, conatining the bit
     * majority from input at each corresponding index
     */
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

    public static void testing() {
         //testing out shift methods
         System.out.println("Shift Methods:");
         System.out.println(shiftLeft("00000000000000000000000000000101", 2)); //Expected: 00000000000000000000000000010100 (20)
         System.out.println(shiftRight("00000000000000000000000001010000", 2)); //Expected: 00000000000000000000000000010100 (20)
 
         //testing out cirlce methods 
         System.out.println("Rotate/circle Methods:");
         System.out.println(rotateLeft("10000000000000000000000000000101", 33)); //Expected: 00000000000000000000000000001011 (11)
         System.out.println(rotateRight("10000000000000000000000000000101", 33)); //Expected: 11000000000000000000000000000010 (3221225474)
 
         // for(int i = 0; i < 32; i++) {
         //     System.out.println(rotateRight("10000000000000000000000000000101", i)); 
         // }
 
         // System.out.println(Long.MAX_VALUE);
         // System.out.println(5>>2); //should be 1

        System.out.println("\nMajory:");
        String bin1 = "00000000000000000000000011000100";
        String bin2 = "00000000000000000000000000011010";
        String bin3 = "00000000000000000000000010101101";
        System.out.println(bin1 + "\n" + bin2 + "\n" + bin3 + "\n--------------------------------");
        System.out.println(majority(bin1, bin2, bin3));

        System.out.println("\nAdd:");
        System.out.println("add(\"10\", \"10\") = 00000000000000000000000000000100\t(Expected)");
        System.out.println("add(\"10\", \"10\") = " + add("10", "10") + "\t(Result)");

        System.out.println("\nChoice:");
        System.out.println(bin1 + "\n" + bin2 + "\n" + bin3 + "\n--------");
        System.out.println(choice(bin1, bin2, bin3));
    }

    public static void main(String[] args) throws Exception {
        testing();
    }
}
