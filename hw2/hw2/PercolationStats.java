package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private Percolation pcf;
    private int len;
    private int numTest;
    private int[] exp;

    /**
     * perform T independent experiments on an N-by-N grid
     * @param N: N-by-N grid
     * @param T: T independent experiments
     * @param pf
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should be greater than 0.");
        }
        pcf = pf.make(N);
        len = N;
        numTest = T;
        exp = new int[T];
        experiments();
    }

    private void experiments() {
        int a = len * len / 3, b = a * 2;
        for (int i = 0; i < numTest; i++) {
            PercolationFactory pcfT = new PercolationFactory();
            Percolation p = pcfT.make(len);
            while (true) {
                int numOpen = StdRandom.uniform(a, b);
                for (int j = 0; j < numOpen;) {
                    int x = StdRandom.uniform(len), y = StdRandom.uniform(len);
                    if (p.isOpen(x, y)) {
                        continue;
                    }
                    p.open(x, y);
                    j++;
                }
                if (p.percolates()) {
                    exp[i] = numOpen;
                    break;
                }
            }
        }
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        return StdStats.mean(exp);
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        return StdStats.stddev(exp);
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(numTest);
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(numTest);
    }

}
