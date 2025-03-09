import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.TreeMap;

public class WordNet {
    private TreeMap<String, ArrayList<Integer>> words;
    private ArrayList<String> wordsIndex;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        words = new TreeMap<>();
        wordsIndex = new ArrayList<>();

        validate(synsets);
        validate(hypernyms);
        In syn = new In(synsets);
        In hyper = new In(hypernyms);

        int i = 0;
        while (!syn.isEmpty()) {
            String line = syn.readLine();
            String[] data = line.split("\\,");
            for (String w : data[1].split(" ")) {
                ArrayList<Integer> val = words.get(w);

                if (val == null) {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(i);
                    words.put(w, ids);
                }
                else {
                    val.add(i);
                    words.put(w, val);
                }
            }
            wordsIndex.add(data[1]);
            i++;
        }

        Digraph wordnet = new Digraph(words.size());
        while ((!hyper.isEmpty())) {
            String line = hyper.readLine();
            String[] data = line.split("\\,");
            for (int j = 1; j < data.length; j++) {
                wordnet.addEdge(Integer.parseInt(data[0]), Integer.parseInt(data[j]));
            }
        }

        // acyclic
        DirectedCycle cycle = new DirectedCycle(wordnet);
        if (cycle.hasCycle()) throw new IllegalArgumentException("invalid diagram");

        // only one ancestor
        int sink = 0;
        for (int u = 0; u < i; u++) {
            int cnt = 0;
            for (int v : wordnet.adj(u)) {
                cnt++;
            }
            if (cnt == 0) sink += 1;
        }
        if (sink != 1) throw new IllegalArgumentException("invalid diagram");

        sap = new SAP(wordnet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return words.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        validate(word);
        return words.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validate(nounA);
        validate(nounB);
        ArrayList<Integer> v = words.get(nounA);
        ArrayList<Integer> w = words.get(nounB);
        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validate(nounA);
        validate(nounB);
        ArrayList<Integer> v = words.get(nounA);
        ArrayList<Integer> w = words.get(nounB);
        int ancestor = sap.ancestor(v, w);
        return wordsIndex.get(ancestor);
    }

    private void validate(String s) {
        if (s == null) throw new IllegalArgumentException("input string is null");
    }


    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        int cnt = 0;
        for (String s : wn.nouns()) cnt++;
        StdOut.println("size: " + cnt);
        StdOut.println("contain bird: " + wn.isNoun("bird"));
        StdOut.println("sap of worm and bird: " + wn.sap("worm", "bird"));
        StdOut.println("distance between worm and bird: " + wn.distance("worm", "bird"));
    }
}
