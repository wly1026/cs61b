package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private double[] fraction;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.T = T;
        if (N <= 0 || T <= 0) {
            throw new java.lang.IndexOutOfBoundsException("N and T should be both > 0");
        }
        fraction = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation X = pf.make(N);
            while (!X.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                X.open(row, col);
            }
            fraction[i] = (double) X.numberOfOpenSites() / (N * N);  // !!!cast double
        }
    } // perform T independent experiments on an N-by-N grid

    public double mean() {
        return StdStats.mean(fraction);
    } // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(fraction);
    } // sample standard deviation of percolation threshold

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.pow(T, 0.5);
    } // low endpoint of 95% confidence interval

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.pow(T, 0.5);
    } // high endpoint of 95% confidence interval

    // comment before submission.
//    public static void main(String[] args) {
//        PercolationFactory pf = new PercolationFactory();
//        PercolationStats test1 = new PercolationStats(3, 10, pf);
//        System.out.printf("μ: %.2f\n", test1.mean());
//        System.out.printf("σ^2: %.2f\n", test1.stddev());
//        System.out.printf("confidence interval: %.2f ~ %.2f ", test1.confidenceLow(), test1.confidenceHigh());
//    }
}
