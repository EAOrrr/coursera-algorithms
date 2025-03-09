/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int opened;
    private int virtualTop;
    private int virtualBottom;
    private boolean[][] grids;
    private WeightedQuickUnionUF connectMap;
    private WeightedQuickUnionUF fullCheckMap;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        opened = 0;
        virtualBottom = n * n + 1;
        virtualTop = n * n;
        grids = new boolean[n][n];
        connectMap = new WeightedQuickUnionUF(n * n + 2);
        fullCheckMap = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids.length; j++) {
                grids[i][j] = false;
            }
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * grids.length + col - 1;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // check if args is valid
        if (row < 1 || row > grids.length || col < 1 || col > grids.length) {
            throw new IllegalArgumentException("");
        }
        int index = getIndex(row, col);
        if (!isOpen(row, col)) {
            opened++;
            grids[row - 1][col - 1] = true;
            if (row - 1 >= 1 && isOpen(row - 1, col)) {
                connectMap.union(index, getIndex(row - 1, col));
                fullCheckMap.union(index, getIndex(row - 1, col));
            }
            if (col - 1 >= 1 && isOpen(row, col - 1)) {
                connectMap.union(index, getIndex(row, col - 1));
                fullCheckMap.union(index, getIndex(row, col - 1));
            }
            if (row + 1 <= grids.length && isOpen(row + 1, col)) {
                connectMap.union(index, getIndex(row + 1, col));
                fullCheckMap.union(index, getIndex(row + 1, col));
            }
            if (col + 1 <= grids.length && isOpen(row, col + 1)) {
                connectMap.union(index, getIndex(row, col + 1));
                fullCheckMap.union(index, getIndex(row, col + 1));
            }

            if (row == 1) {
                connectMap.union(index, virtualTop);
                fullCheckMap.union(index, virtualTop);
            }
            if (row == grids.length) {
                connectMap.union(index, virtualBottom);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // check if args is valid
        if (row < 1 || row > grids.length || col < 1 || col > grids.length) {
            throw new IllegalArgumentException("");
        }
        return grids[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // check if args is valid
        if (row < 1 || row > grids.length || col < 1 || col > grids.length) {
            throw new IllegalArgumentException("");
        }
        return isOpen(row, col) && fullCheckMap.find(getIndex(row, col)) == fullCheckMap.find(
                virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opened;
    }

    // does the system percolate?
    public boolean percolates() {
        return connectMap.find(virtualBottom) == connectMap.find(
                virtualTop);
    }

    // test client (optional)
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        Percolation per = new Percolation(3);
        per.open(2, 3);
        per.open(1, 3);
        // per.open(1, 2);
        per.open(3, 3);
        per.open(3, 1);
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                StdOut.println(per.isFull(i, j));
            }
        }
        // while (per.numberOfOpenSites() != n * n) {
        //     while (true) {
        //         int r = StdRandom.uniformInt(n) + 1;
        //         int c = StdRandom.uniformInt(n) + 1;
        //         StdOut.println(per.isFull(r, c));
        //         int row = StdRandom.uniformInt(n) + 1;
        //         int col = StdRandom.uniformInt(n) + 1;
        //         if (!per.isOpen(row, col)) {
        //             per.open(row, col);
        //             break;
        //         }
        //
        //     }
        //
        // }
    }
}
