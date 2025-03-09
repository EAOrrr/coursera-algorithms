/* *****************************************************************************
 *  Name: Huffman.java
 *  Execution:    java Huffman - < input.txt   (compress)
 *  Execution:    java Huffman + < input.txt   (expand)
 *  Dependency: MinPQ.java BinaryStdOut.java BinaryStdIn.java
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Huffman {
    private static final int R = 256;

    private Huffman() {
    }

    private static class Node implements Comparable<Node> {
        private char c;
        private int freq;
        private Node left, right;

        public Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node other) {
            return Integer.compare(freq, other.freq);
        }
    }

    public static void encode() {
        char[] freq = new char[R];
        // read the input
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }
        // build the Trie
        Node root = buildTrie(freq);
        String[] table = new String[R];
        buildCode(root, table, "");

        writeTrie(root);

        BinaryStdOut.write(input.length);

        for (int i = 0; i < input.length; i++) {
            writeCode(input[i], table);
        }
        BinaryStdOut.close();
    }

    private static void buildCode(Node x, String[] st, String soFar) {
        if (x.isLeaf()) {
            st[x.c] = soFar;
            return;
        }
        buildCode(x.left, st, soFar + "0");
        buildCode(x.right, st, soFar + "1");
    }

    private static Node buildTrie(char[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char i = 0; i < R; i++) {
            if (freq[i] != 0) pq.insert(new Node(i, freq[i], null, null));
        }
        while (pq.size() > 1) {
            Node n1 = pq.delMin();
            Node n2 = pq.delMin();
            Node n = new Node('\0', n1.freq + n2.freq, n1, n2);
            pq.insert(n);
        }
        return pq.delMin();
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.c);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static void writeCode(char c, String[] st) {
        String bin = st[c];
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) == '1') BinaryStdOut.write(true);
            else BinaryStdOut.write(false);
        }
    }


    public static void decode() {
        Node root = readTrie();
        String[] st = new String[R];
        buildCode(root, st, "");
        int cnt = BinaryStdIn.readInt();
        for (int i = 0; i < cnt; i++) {
            writeChar(root);
        }
        BinaryStdOut.close();
    }

    private static void writeChar(Node root) {
        Node x = root;
        while (!x.isLeaf()) {
            if (BinaryStdIn.readBoolean()) {
                x = x.right;
            }
            else {
                x = x.left;
            }
        }
        BinaryStdOut.write(x.c);
    }

    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        }
        return new Node('\0', 0, readTrie(), readTrie());
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
