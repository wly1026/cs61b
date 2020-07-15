package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int lengthOfSide;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridOnlyTop;       //!!! avoid backwash.
    private int[] openOrLock ;               // 0 is for lock, 1 is for open.
    private int vTop;
    private int vBottom;
    private int numberOfOpenSites = 0;

    public Percolation(int N) {
        lengthOfSide = N;
        grid = new WeightedQuickUnionUF(N * N + 2);
        gridOnlyTop = new WeightedQuickUnionUF(N * N + 1);
        openOrLock = new int[N * N];
        vTop = N * N;
        vBottom = N * N + 1;

        for(int i = 0; i < (N * N); ++i) {
            openOrLock[i] = 0;
        }
    } // create N-by-N grid, with all sites initially blocked

    private int xyTo1D(int x, int y) {
        return x * lengthOfSide + y;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= lengthOfSide || col < 0 || col >= lengthOfSide) {
            throw new java.lang.IndexOutOfBoundsException("indices are between 0 and N − 1");
        }
    }

    public void open(int row, int col) {
        validate(row, col);

        int index = xyTo1D(row, col);
        if (!isOpen(row, col)) {
            openOrLock[index] = 1;
            numberOfOpenSites += 1;
            if (row == 0) {
                grid.union(index, vTop);
                gridOnlyTop.union(index, vTop);
            }

            if (row > 0 && isOpen(row - 1, col)) {
                grid.union(index, xyTo1D(row - 1, col));
                gridOnlyTop.union(index, xyTo1D(row - 1, col));
            }
            if (row < (lengthOfSide - 1) && isOpen(row + 1, col)) {
                grid.union(index, xyTo1D(row + 1, col));
                gridOnlyTop.union(index, xyTo1D(row + 1, col));
            }
            if (col > 0 && isOpen(row, col - 1)) {
                grid.union(index, xyTo1D(row, col - 1));
                gridOnlyTop.union(index, xyTo1D(row, col - 1));
            }
            if (col < (lengthOfSide - 1) && isOpen(row, col + 1)) {
                grid.union(index, xyTo1D(row, col + 1));
                gridOnlyTop.union(index, xyTo1D(row, col + 1));
            }
            if (row == (lengthOfSide - 1)) {
                grid.union(index, vBottom);
            }
        }
    } // open the site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        validate(row, col);

        return openOrLock[xyTo1D(row, col)] == 1;
    } // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        validate(row, col);

        return gridOnlyTop.connected(vTop, xyTo1D(row, col));
    } // is the site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    } // number of open sites

    public boolean percolates() {
        return grid.connected(vTop, vBottom);
    } // does the system percolate?

    public static void main(String[] args) {

    }
    // use for unit testing (not required, but keep this here for the autograder)

}
