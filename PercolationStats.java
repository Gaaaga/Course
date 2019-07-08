import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int testCount = 0;
    private double[] resultArray;
    private Percolation pr;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        testCount = trials;
        resultArray = new double[trials];
        for (int i = 0; i < trials ; i++ ) {
            pr = new Percolation(n);
            while (!pr.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                pr.open(row,col);
            }
            double result = (double)pr.numberOfOpenSites() / (n * n);
            resultArray[i] = result;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        if (mean == 0)
            mean = StdStats.mean(resultArray);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddev == 0)
            stddev = StdStats.stddev(resultArray);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(testCount);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(testCount);
    }

     // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n,trials);

        String confidence ="[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
