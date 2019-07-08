import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int top = 0;
    private int bottom;
    private boolean[][] site;
    private WeightedQuickUnionUF qf;
    private WeightedQuickUnionUF fuf;
    private int openSites = 0;

    private int getIndex(int row,int col) {
        return size * (row - 1) + col;
    }
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n<=0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = n;
        bottom = size * size +1;
        site = new boolean[size][size]; //  will be initialized to false by default;
        qf = new WeightedQuickUnionUF(size * size + 2);
        fuf = new WeightedQuickUnionUF(size * size + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(isOpen(row, col)) {
            return;
        }
        if(row > 0 && row <=size && col > 0 && col <= size) {
            site[row - 1][col - 1] = true;
            openSites += 1;
            if (row == 1) {
                qf.union(getIndex(row, col), top);
                fuf.union(getIndex(row, col), top);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                qf.union(getIndex(row, col), getIndex(row - 1, col));
                fuf.union(getIndex(row, col), getIndex(row - 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                qf.union(getIndex(row, col), getIndex(row, col - 1));
                fuf.union(getIndex(row, col), getIndex(row, col - 1));
            }
            if (row < size && isOpen(row + 1, col)) {
                qf.union(getIndex(row, col), getIndex(row + 1, col));
                fuf.union(getIndex(row, col), getIndex(row + 1, col));
            }
            if (col < size && isOpen(row, col + 1)) {
                qf.union(getIndex(row, col), getIndex(row, col + 1));
                fuf.union(getIndex(row, col), getIndex(row, col + 1));
            }
            if (row == size) {
                qf.union(getIndex(row, col), bottom);
            }
        } else {
            throw new java.lang.IllegalArgumentException();
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row > 0 && row <=size && col > 0 && col <= size) {
            return site[row - 1][col - 1];
        } else {
            throw new java.lang.IllegalArgumentException();
        }

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(row > 0 && row <=size && col > 0 && col <= size) {
            return fuf.connected(getIndex(row, col), top);
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
