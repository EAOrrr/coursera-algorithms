/* *****************************************************************************
 *  Name: Huffman.java
 *  Execution:    java BurrowsWheeler - < input.txt   (compress)
 *  Execution:    java BurrowsWheeler+ < input.txt   (expand)
 *  Dependency: BinaryStdOut.java BinaryStdIn.java CircularSuffix.java
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);

        int first = 0;
        for (int i = 0; i < s.length(); i++) {
            if (circularSuffixArray.index(i) == 0) {
                first = i;
                break;
            }
        }

        BinaryStdOut.write(first);

        for (int i = 0; i < s.length(); i++) {
            int index = circularSuffixArray.index(i);
            BinaryStdOut.write(s.charAt((index + s.length() - 1) % s.length()));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] ss = s.toCharArray();
        Node[] nodes = new Node[s.length()];

        for (int i = 0; i < s.length(); i++) {
            nodes[i] = new Node(ss[i], i);
        }
        Node[] sorted = countingSort(nodes);
        int curr = first;
        for (int i = 0; i < s.length(); i++) {
            BinaryStdOut.write(sorted[curr].ch);
            curr = sorted[curr].index;
        }
        BinaryStdOut.close();
    }

    private static Node[] countingSort(Node[] nodes) {
        int[] freq = new int[R + 1];
        Node[] result = new Node[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            freq[nodes[i].ch]++;
        }
        for (int i = 1; i < R; i++) {
            freq[i] = freq[i] + freq[i - 1];
        }
        for (int i = nodes.length - 1; i >= 0; i--) {
            result[freq[nodes[i].ch] - 1] = nodes[i];
            freq[nodes[i].ch]--;
        }
        return result;

    }

    private static class Node implements Comparable<Node> {
        private char ch;
        private int index;

        public Node(char c, int i) {
            ch = c;
            index = i;
        }

        public int compareTo(Node other) {
            return Character.compare(ch, other.ch);
        }

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
