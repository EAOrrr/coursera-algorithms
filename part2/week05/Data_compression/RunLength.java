/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLength {
    private static final int R = 256;
    private static final int LG_R = 8;

    private RunLength() {
    }

    /**
     * Reads a sequence of bits from standard input (that are encoded
     * using run-length encoding with 8-bit run lengths); decodes them;
     * and writes the results to standard output.
     */
    public static void expand() {
        boolean b = false;
        while (!BinaryStdIn.isEmpty()) {
            int cnt = BinaryStdIn.readInt(LG_R);
            for (int i = 0; i < cnt; i++) {
                BinaryStdOut.write(b);
            }
            b = !b;
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input; compresses
     * them using run-length coding with 8-bit run lengths; and writes the
     * results to standard output.
     */
    public static void compress() {
        char cnt = 0;
        boolean old = false;
        while (!BinaryStdIn.isEmpty()) {
            boolean b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(cnt, LG_R);
                cnt = 1;
                old = !old;
            }
            else {
                if (cnt == R - 1) {
                    BinaryStdOut.write(cnt, LG_R);
                    cnt = 0;
                    BinaryStdOut.write(cnt, LG_R);
                }
                cnt++;
            }
        }

        BinaryStdOut.write(cnt, LG_R);
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
