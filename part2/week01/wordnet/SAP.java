import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("argument G is null");
        g = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(g, w);
        int min = Integer.MAX_VALUE;
        for (int ancestor = 0; ancestor < g.V(); ancestor++) {
            if (bv.hasPathTo(ancestor) && bw.hasPathTo(ancestor)) {
                if (min > bv.distTo(ancestor) + bw.distTo(ancestor)) {
                    min = bv.distTo(ancestor) + bw.distTo(ancestor);
                }
            }
        }
        return min;
    }


    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(g, w);
        int sap = -1;
        int min = Integer.MAX_VALUE;
        for (int ancestor = 0; ancestor < g.V(); ancestor++) {
            if (bv.hasPathTo(ancestor) && bw.hasPathTo(ancestor)) {
                if (min > bv.distTo(ancestor) + bw.distTo(ancestor)) {
                    sap = ancestor;
                    min = bv.distTo(ancestor) + bw.distTo(ancestor);
                }
            }
        }
        return sap;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("argument to length() is null");
        for (int v0 : v) {
            validate(v0);
        }
        for (int w0 : w) {
            validate(w0);
        }
        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(g, w);
        int min = Integer.MAX_VALUE;
        for (int ancestor = 0; ancestor < g.V(); ancestor++) {
            if (bv.hasPathTo(ancestor) && bw.hasPathTo(ancestor)) {
                if (min > bv.distTo(ancestor) + bw.distTo(ancestor)) {
                    min = bv.distTo(ancestor) + bw.distTo(ancestor);
                }
            }
        }
        return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("argument to lenght() is null");
        for (int v0 : v) {
            validate(v0);
        }
        for (int w0 : w) {
            validate(w0);
        }
        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(g, w);
        int min = Integer.MAX_VALUE;
        int sap = -1;
        for (int ancestor = 0; ancestor < g.V(); ancestor++) {
            if (bv.hasPathTo(ancestor) && bw.hasPathTo(ancestor)) {
                if (min > bv.distTo(ancestor) + bw.distTo(ancestor)) {
                    min = bv.distTo(ancestor) + bw.distTo(ancestor);
                    sap = ancestor;
                }
            }
        }
        StdOut.println(bv.distTo(sap) + " " + bw.distTo(sap));
        return sap;
    }

    private void validate(int u) {
        if (u < 0 || u >= g.V()) throw new IllegalArgumentException("input vertice out of index");
    }

    private Integer bfsStep(int v, int[] dists[], int)

    // do unit testing of this class
    public static void main(String[] args) {
        // In in = new In(args[0]);
        // Digraph G = new Digraph(in);
        // SAP sap = new SAP(G);
        // while (!StdIn.isEmpty()) {
        //     int v = StdIn.readInt();
        //     int w = StdIn.readInt();
        //     int length = sap.length(v, w);
        //     int ancestor = sap.ancestor(v, w);
        //     StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        // }

        In in = new In("digraph25.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Stack<Integer> v = new Stack<>();
        Stack<Integer> w = new Stack<>();
        v.push(13);
        v.push(23);
        v.push(24);
        w.push(6);
        w.push(17);
        w.push(16);
        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}
