/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private int move;
    private boolean isSolvable;
    private Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("input null board");
        Board twin = initial.twin();
        Node initialRoot = new Node(initial, null, 0);
        Node twinRoot = new Node(twin, null, 0);

        MinPQ<Node> initialPQ = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();

        initialPQ.insert(initialRoot);
        twinPQ.insert(twinRoot);

        Node initSol = null;
        while (!initialPQ.isEmpty() && !twinPQ.isEmpty()) {
            // deal with initialPQ
            initSol = next(initialPQ);
            // deal with twinSol
            Node twinSol = next(twinPQ);
            // Node twinSol = null;

            if (initSol != null || twinSol != null) {
                break;
            }
        }
        if (initSol != null) {
            isSolvable = true;
            move = initSol.moveSoFar;
            solution = buildSolution(initSol);
            return;
        }
        isSolvable = false;
        move = -1;
        solution = null;


    }

    private Stack<Board> buildSolution(Node solNode) {
        Stack<Board> result = new Stack<>();
        while (solNode != null) {
            result.push(solNode.getData());
            solNode = solNode.getPrev();
        }
        return result;
    }

    private Node next(MinPQ<Node> pq) {
        Node curr = pq.delMin();
        if (curr.getData().isGoal()) {
            return curr;
        }
        int currMove = curr.getMove();

        for (Board neighbor : curr.getData().neighbors()) {
            if (curr.getPrev() == null || !neighbor.equals(curr.getPrev().getData())) {
                pq.insert(new Node(neighbor, curr, currMove + 1));
            }
        }
        return null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private class Node implements Comparable<Node> {
        private Node prev;
        private Board data;
        private int moveSoFar;
        private int priority;

        public Node(Board d, Node p, int m) {
            data = d;
            prev = p;
            moveSoFar = m;
            priority = moveSoFar + data.manhattan();
        }

        public Board getData() {
            return data;
        }

        public Node getPrev() {
            return prev;
        }

        public int getMove() {
            return moveSoFar;
        }

        public int compareTo(Node other) {
            // if (data.manhattan() + moveSoFar < other.data.manhattan() + other.moveSoFar)
            //     return -1;
            // if (data.manhattan() + moveSoFar > other.data.manhattan() + other.moveSoFar)
            //     return 1;
            // return Integer.compare(data.hamming() + moveSoFar,
            //                        other.data.hamming() + other.moveSoFar);
            return Integer.compare(priority,
                                   other.priority);
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
            StdOut.println("Minimum number of moves = " + solver.moves());

        }
    }

}
