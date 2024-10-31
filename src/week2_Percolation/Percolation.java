package week2_Percolation;

import edu.princeton.cs.algs4.QuickFindUF;
import java.util.Arrays;

public class Percolation {

    enum Site {
        BLOCKED, OPEN
    }

    Site[][] grid;
    QuickFindUF uf;
    QuickFindUF ufNoBottom;

    int virtualStartRoot;
    int virtualEndRoot;

    int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        uf = new QuickFindUF(n * n + 2);
        ufNoBottom = new QuickFindUF(n * n + 2);

        virtualStartRoot = n * n;
        virtualEndRoot = n * n + 1;

        grid = new Site[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(grid[i], Site.BLOCKED);
        }
        connectTopAndBottomRows(n);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isWithinBounds(row, col))
            throw new IllegalArgumentException();

        if (grid[row - 1][col - 1] == Site.OPEN) return;

        grid[row - 1][col - 1] = Site.OPEN;
        numberOfOpenSites++;

        connectToNeighbors(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isWithinBounds(row, col))
            throw new IllegalArgumentException();

        return grid[row - 1][col - 1] != Site.BLOCKED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isWithinBounds(row, col))
            throw new IllegalArgumentException();

        if (grid[row - 1][col - 1] == Site.BLOCKED)
            return false;

        return ufNoBottom.connected(xyTo1D(row, col), virtualStartRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualStartRoot, virtualEndRoot);
    }

    //region Private methods
    private int xyTo1D(int row, int col) {
        return ((row - 1) * grid.length) + (col - 1);
    }

    private void connectToNeighbors(int row, int col) {
        connectIfOpen(row, col, row - 1, col); //  Top
        connectIfOpen(row, col, row + 1, col); //  Bottom
        connectIfOpen(row, col, row, col - 1); //  Left
        connectIfOpen(row, col, row, col + 1); //  Right
    }

    private void connectIfOpen(
        int row,
        int col,
        int neighborRow,
        int neighborCol
    ) {
        if (isWithinBounds(neighborRow, neighborCol) &&
            grid[neighborRow - 1][neighborCol - 1] != Site.BLOCKED
        ) {
            uf.union(xyTo1D(row, col), xyTo1D(neighborRow, neighborCol));
            ufNoBottom.union(xyTo1D(row, col), xyTo1D(neighborRow, neighborCol));
        }
    }

    private boolean isWithinBounds(int row, int col) {
        return row > 0 && row <= grid.length && col > 0 && col <= grid.length;
    }

    private void connectTopAndBottomRows(int n) {
        for (int j = 0; j < n; j++) {
            uf.union(xyTo1D(1, j + 1), virtualStartRoot);
            ufNoBottom.union(xyTo1D(1, j + 1), virtualStartRoot);

            uf.union(xyTo1D(n, j + 1), virtualEndRoot);
        }
    }
//endregion
}
