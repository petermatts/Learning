package crypto;

/**
 * https://en.wikipedia.org/wiki/Bitwise_operation
 */
public class Bitwise {
    /**** Instance variables for generalized testing ****/

    static final int i_bin = 2;
    static final long l_bin = 987654320;
    static final short s_bin = 5;
    static final char c_bin = 'h';
    static final byte b_bin = 7;
    static final int distance = 1;

    /**** End variables****/

    /**** Left Shift ****/

    private static int shiftLeft(int bin, int distance) { return bin<<distance; }
    private static long shiftLeft(long bin, int distance) { return bin<<distance; }
    private static short shiftLeft(short bin, int distance) { return (short) (bin<<distance); }
    private static char shiftLeft(char bin, int distance) { return (char) (bin<<distance); }
    private static byte shiftLeft(byte bin, int distance) { return (byte) (bin<<distance); }

    /**** End Left Shift ****/


    /**** Right Shift ****/

    private static int shiftRight(int bin, int distance) { return bin>>distance; }
    private static long shiftRight(long bin, int distance) { return bin>>distance; }
    private static short shiftRight(short bin, int distance) { return (short) (bin>>distance); }
    private static char shiftRight(char bin, int distance) { return (char) (bin>>distance); }
    private static byte shiftRight(byte bin, int distance) { return (byte) (bin>>distance); }

    /**** End Right Shift ****/


    /**** Rotate Left ****/

    private static int rotateLeft(int bin, int distance) { return Integer.rotateLeft(bin, distance); }
    private static long rotateLeft(long bin, int distance) { return Long.rotateLeft(bin, distance); }
    private static short rotateLeft(short bin, int distance) { Short s = bin; return (short) Integer.rotateLeft(s.intValue(), distance); }
    private static char rotateLeft(char bin, int distance) { return (char) Integer.rotateLeft(Character.hashCode(bin), distance); }
    private static byte rotateLeft(byte bin, int distance) { Byte b = bin; return (byte) Integer.rotateLeft(b.intValue(), distance); }

    /**** End Rotate Left ****/


    /**** Rotate Right ****/

    private static int rotateRight(int bin, int distance) { return Integer.rotateRight(bin, distance); }
    private static long rotateRight(long bin, int distance) { return Long.rotateRight(bin, distance); }
    private static short rotateRight(short bin, int distance) { Short s = bin; return (short) Integer.rotateRight(s.intValue(), distance); }
    private static char rotateRight(char bin, int distance) { return (char) Integer.rotateRight(Character.hashCode(bin), distance); }
    private static byte rotateRight(byte bin, int distance) { Byte b = bin; return (byte) Integer.rotateRight(b.intValue(), distance); }

    /**** End Rotate Right ****/


    /**** AND ****/
    //TODO
    /**** End AND ****/


    /**** OR ****/
    //TODO
    /**** END OR ****/


    /**** XOR ****/
    //TODO
    /**** END XOR ****/

    /**** Compliment ****/
    //TODO
    /**** END Compliment ****/


    /**** Support eethods for main printing ****/

    public static void printVars() {
        System.out.println("i_bin = " + i_bin);
        System.out.println("l_bin = " + l_bin);
        System.out.println("s_bin = " + s_bin);
        System.out.println("c_bin = " + c_bin);
        System.out.println("b_bin = " + b_bin);
        System.out.println("distance = " + distance);
    }

    public static void printLeftShift() {
        System.out.println("\nLeft Shift");
        System.out.println(i_bin + " << " + distance + " = " + shiftLeft(i_bin, distance));
        System.out.println(l_bin + " << " + distance + " = " + shiftLeft(l_bin, distance));
        System.out.println(s_bin + " << " + distance + " = " + shiftLeft(s_bin, distance));
        System.out.println(c_bin + " << " + distance + " = " + shiftLeft(c_bin, distance));
        System.out.println(b_bin + " << " + distance + " = " + shiftLeft(b_bin, distance));
    }

    public static void printRightShift() {
        System.out.println("\nRight Shift");
        System.out.println(i_bin + " >> " + distance + " = " + shiftRight(i_bin, distance));
        System.out.println(l_bin + " >> " + distance + " = " + shiftRight(l_bin, distance));
        System.out.println(s_bin + " >> " + distance + " = " + shiftRight(s_bin, distance));
        System.out.println(c_bin + " >> " + distance + " = " + shiftRight(c_bin, distance));
        System.out.println(b_bin + " >> " + distance + " = " + shiftRight(b_bin, distance));
    }

    public static void printLeftRotate() {
        System.out.println("\nRotate Left");
        System.out.println("L_Rotate-" + distance + " " + i_bin + " = " + rotateLeft(i_bin, distance));
        System.out.println("L_Rotate-" + distance + " " + l_bin + " = " + rotateLeft(l_bin, distance));
        System.out.println("L_Rotate-" + distance + " " + s_bin + " = " + rotateLeft(s_bin, distance));
        System.out.println("L_Rotate-" + distance + " " + c_bin + " = " + rotateLeft(c_bin, distance));
        System.out.println("L_Rotate-" + distance + " " + b_bin + " = " + rotateLeft(b_bin, distance));
    }

    public static void printRightRotate() {
        System.out.println("\nRotate Right");
        System.out.println("R_Rotate-" + distance + " " + i_bin + " = " + rotateRight(i_bin, distance));
        System.out.println("R_Rotate-" + distance + " " + l_bin + " = " + rotateRight(l_bin, distance));
        System.out.println("R_Rotate-" + distance + " " + s_bin + " = " + rotateRight(s_bin, distance));
        System.out.println("R_Rotate-" + distance + " " + c_bin + " = " + rotateRight(c_bin, distance));
        System.out.println("R_Rotate-" + distance + " " + b_bin + " = " + rotateRight(b_bin, distance));
    }

    /**** End support methods for main printing****/

    public static void main(String[] args) {        
        // printVars();
        // printLeftShift();
        // printRightShift();
        // printLeftRotate();
        printRightRotate();
    }
}
