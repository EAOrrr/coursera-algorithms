/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private int blankRow, blankCol;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("input null tiles");

        int dim = tiles.length;
        hamming = 0;
        manhattan = 0;

        this.tiles = new int[dim][dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                this.tiles[r][c] = tiles[r][c];
                if (tiles[r][c] == 0) {
                    blankRow = r;
                    blankCol = c;
                }
                else {
                    if (tiles[r][c] != goal(r, c)) {
                        hamming++;
                        manhattan += perManhattan(r, c);
                    }
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(tiles.length + "\n");
        for (int r = 0; r < tiles.length; r++) {
            s.append(' ');
            for (int c = 0; c < tiles.length; c++) {
                s.append(tiles[r][c] + " ");
            }
            s.append('\n');
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles.length; c++) {
                if (tiles[r][c] != goal(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() == getClass()) {
            if (dimension() != ((Board) y).dimension()) return false;
            return Arrays.deepEquals(tiles, ((Board) y).tiles);
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        if (blankRow - 1 >= 0) {
            int[][] neighborTiles = deepCopy(tiles);
            exch(neighborTiles, blankRow, blankCol, blankRow - 1, blankCol);
            Board neighbor = new Board(neighborTiles);
            neighbors.enqueue(neighbor);

        }
        if (blankRow + 1 < tiles.length) {
            int[][] neighborTiles = deepCopy(tiles);
            exch(neighborTiles, blankRow, blankCol, blankRow + 1, blankCol);
            Board neighbor = new Board(neighborTiles);
            neighbors.enqueue(neighbor);
        }
        if (blankCol - 1 >= 0) {
            int[][] neighborTiles = deepCopy(tiles);
            exch(neighborTiles, blankRow, blankCol, blankRow, blankCol - 1);
            Board neighbor = new Board(neighborTiles);
            neighbors.enqueue(neighbor);
        }

        if (blankCol + 1 < tiles.length) {
            int[][] neighborTiles = deepCopy(tiles);
            exch(neighborTiles, blankRow, blankCol, blankRow, blankCol + 1);
            Board neighbor = new Board(neighborTiles);
            neighbors.enqueue(neighbor);
        }
        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = deepCopy(tiles);
        if (blankRow == 0 && blankCol == 0) {
            // twin.exch(blankRow+1, blankCol + 0, blankRow+1, blankCol+1);
            exch(twinTiles, 1, 0, 1, 1);
        }
        else {
            if (blankRow == 0 && blankCol == 1)
                exch(twinTiles, 0, 0, 1, 0);
            else
                exch(twinTiles, 0, 0, 0, 1);
        }
        return new Board(twinTiles);
    }


    // compute the entry of goal board given row and col
    private int goal(int row, int col) {
        if (row == tiles.length - 1 && col == tiles.length - 1) return 0;
        return tiles.length * row + col + 1;
    }

    // compute the row and col of given entry of the goal board (stored in array)
    private int[] rowAndCol(int entry) {
        int[] rowCol = new int[2];
        rowCol[0] = (entry - 1) / tiles.length; // row
        rowCol[1] = (entry - 1) % tiles.length; // col;
        return rowCol;
    }

    // compute Manhattan distance per entry (except for zero)
    private int perManhattan(int row, int col) {
        int[] expected = rowAndCol(tiles[row][col]);
        int expectedRow = expected[0], expectedCol = expected[1];
        return Math.abs(row - expectedRow) + Math.abs(col - expectedCol);
    }

    private static void exch(int[][] tiles, int r1, int c1, int r2, int c2) {
        int temp = tiles[r1][c1];
        tiles[r1][c1] = tiles[r2][c2];
        tiles[r2][c2] = temp;
    }

    private static int[][] deepCopy(int[][] tiles) {
        int n = tiles.length;
        int[][] newTiles = new int[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                newTiles[r][c] = tiles[r][c];
            }
        }
        return newTiles;

    }

    // unit testing (not graded)
    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            // print
            StdOut.println(initial);
            // test manhattan distance and hamming
            StdOut.println("Hamming: " + initial.hamming());
            StdOut.println("Manhattan dist: " + initial.manhattan());
            // test isGoal
            StdOut.println("isGoal: " + initial.isGoal());
            // test dimension
            StdOut.println("dimension: " + initial.dimension());
            // test neighbors
            // Board nei = new Board(tiles);
            // for (Board neighbor : initial.neighbors()) {
            //     StdOut.println(neighbor);
            // }

            // test equality
            // StdOut.println("equals: " + initial.equals(initial));
            // StdOut.println("equals: " + initial.equals(another));
            // StdOut.println("equals: " + initial.equals(nei));
            // StdOut.println("equals: " + initial.equals(null));
            StdOut.println("twin: ");
            StdOut.println(initial.twin());
            StdOut.println();
            // Board twin = initial;
            // for (int i = 0; i < 13; i++) {
            //     twin = twin.twin();
            // }
            // StdOut.println(twin);
            // StdOut.println(twin.hamming());
        }
    }
}
