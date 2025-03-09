import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;

    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        wn = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        // StdOut.println(nouns.length);
        int maxDis = 0;
        String outcast = "";
        for (String n : wn.nouns()) {
            int distance = 0;
            for (String s : nouns) {
                distance += wn.distance(n, s);
            }
            if (distance > maxDis) {
                maxDis = distance;
                outcast = n;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
