/* *****************************************************************************
 *  Name: CircularSuffix.java
 *  Execution:    none
 *  Dependency:   none
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {
    private int length;
    private char[] newS;
    private Substr[] substrings;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("calls CircularSuffixArray() with null");
        length = s.length();
        newS = new char[2 * length];
        substrings = new Substr[length];
        for (int i = 0; i < length; i++) {
            substrings[i] = new Substr(i);
            newS[i] = s.charAt(i);
            newS[i + length] = s.charAt(i);
        }
        // StdOut.println("constructed finish");

        Arrays.sort(substrings);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length) throw new IllegalArgumentException("i out of index");
        return substrings[i].index;
    }

    private class Substr implements Comparable<Substr> {
        private int index;

        public Substr(int i) {
            index = i;
        }

        public int compareTo(Substr other) {
            for (int i = 0; i < length; i++) {
                if (newS[i + index] < newS[other.index + i]) return -1;
                if (newS[i + index] > newS[other.index + i]) return 1;
            }
            return 0;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String s = StdIn.readString();
        CircularSuffixArray c = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); i++) {
            StdOut.println(c.index(i));
        }
    }

}
