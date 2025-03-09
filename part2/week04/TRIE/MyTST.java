/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MyTST<Value> {
    private Node root;  // root of trie
    private int n;      // number of keys in trie

    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }

    public MyTST() {
        // root = new Node();
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        // if (key.equals("")) return (Value) root.val;
        // Node x = get(root.mid, key, 0);
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if (c < x.c) return get(x.left, key, d);
        else if (x.c < c) return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid, key, d + 1);
        else return x;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
            // if (val == null) delete(key);
        else root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) x.left = put(x.left, key, val, d);
        else if (x.c < c) x.right = put(x.right, key, val, d);
        else if (d == key.length() - 1) {
            if (x.val != null) n++;
            x.val = val;
        }
        else x.mid = put(x.mid, key, val, d + 1);

        return x;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null");
        }
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        if (x == null) return results;
        if (x.val != null) results.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> q) {
        if (x == null) return;
        collect(x.left, prefix, q);
        collect(x.right, prefix, q);
        if (x.val != null) q.enqueue(prefix.toString() + x.c);
        prefix.append(x.c);
        collect(x.mid, prefix, q);
        prefix.deleteCharAt(prefix.length() - 1);

    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new Queue<String>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }

    private void collect(Node x, StringBuilder prefix, int i, String pattern,
                         Queue<String> queue) {
        if (x == null || i >= pattern.length()) return;

        char c = pattern.charAt(i);
        if (x.c < c || c == '.') collect(x.right, prefix, i, pattern, queue);
        if (x.c > c || c == '.') collect(x.left, prefix, i, pattern, queue);
        if (x.c == c || c == '.') {
            prefix.append(x.c);
            if (prefix.length() == pattern.length() && x.val != null)
                queue.enqueue(prefix.toString());
            collect(x.mid, prefix, i + 1, pattern, queue);
            prefix.deleteCharAt(prefix.length() - 1);
        }

    }

    public String longestPrefixOf(String query) {
        if (query == null)
            throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (d == query.length()) return length;
        char c = query.charAt(d);

        if (x.c < c) return longestPrefixOf(x.right, query, d, length);
        else if (x.c > c) return longestPrefixOf(x.left, query, d, length);
        else {
            if (x.val != null) length = d + 1;
            return longestPrefixOf(x.mid, query, d + 1, length);
        }

    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if (x.c < c) x.right = delete(x.right, key, d);
        else if (x.c > c) x.left = delete(x.left, key, d);
        else if (d < key.length() - 1) x.mid = delete(x.mid, key, d + 1);
        else {
            if (x.val != null) n--;
            x.val = null;
        }

        if (x.val != null || x.mid != null) return x;
        return delete(x);
        // if (x.left == null && x.right == null) return null;
        // if (x.left == null) return x.right;
        // if (x.right == null) return x.left;
        // Node temp = findMin(x.right);
        // if (temp == x.right) {
        //     Node tmp = x.left;
        //     x = x.right;
        //     x.left = tmp;
        // }
        // else {
        //     temp.left = x.left;
        //     temp.right = x.right;
        //     x = temp;
        // }
        // return x;
    }

    private Node delete(Node x) {
        if (x.left == null && x.right == null) return null;
        if (x.left == null) return x.right;
        if (x.right == null) return x.left;
        Node t = x;
        x = findMin(t.right);
        x.right = deleteMin(t.right);
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    private Node findMin(Node x) {
        // Node parent = x, curr = x.left;
        // if (curr == null) return parent;
        // while (curr.left != null) {
        //     parent = curr;
        //     curr = curr.left;
        // }
        // parent.left = curr.right;
        // curr.right = null;
        // return curr;
        while (x.left != null) {
            x = x.left;
        }
        return x;
    }

    public static void main(String[] args) {
        MyTST<Integer> st = new MyTST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        st.delete("sea");
        st.delete("hey");
        st.delete("shells");


        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }
        //
        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        // st.delete("shell");

        StdOut.println("longestPrefixOf(\"shell\"):");
        StdOut.println(st.longestPrefixOf("shell"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();
        //
        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);

    }
}
