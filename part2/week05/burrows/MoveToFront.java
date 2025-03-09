/* *****************************************************************************
 *  Name: Huffman.java
 *  Execution:    java MoveToFront - < input.txt   (compress)
 *  Execution:    java MoveToFront + < input.txt   (expand)
 *  Dependency: BinaryStdOut.java BinaryStdIn.java
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // initialize the alphabet
        char[] alphabet = new char[R];
        for (char i = 0; i < R; i++) {
            alphabet[i] = i;
        }
        // moveToFront
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            char i = indexOf(alphabet, c);
            BinaryStdOut.write(i);
            moveFront(alphabet, i);
        }
        BinaryStdOut.close();
    }

    private static char indexOf(char[] alphabet, char c) {
        for (char i = 0; i < R; i++) {
            if (alphabet[i] == c) return i;
        }
        return 0;
    }

    private static void moveFront(char[] alphabet, char i) {
        char c = alphabet[i];
        for (char j = i; j > 0; j--) {
            alphabet[j] = alphabet[j - 1];
        }
        alphabet[0] = c;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphabet = new char[R];
        for (char i = 0; i < R; i++) {
            alphabet[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char i = BinaryStdIn.readChar();
            BinaryStdOut.write(alphabet[i]);
            moveFront(alphabet, i);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
