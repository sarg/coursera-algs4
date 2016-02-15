import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    public PercolationStats(int N, int T)     // perform T independent
    // experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();

        results = new double[T];

        for (int i = 0; i < T; i++) {
            results[i] = perform(N);
        }
    }

    private double perform(int n) {
        Percolation p = new Percolation(n);
        int x;
        for (x = 0; !p.percolates(); x++) {
            int i, j;
            do {
                i = StdRandom.uniform(n) + 1;
                j = StdRandom.uniform(n) + 1;
            } while (p.isOpen(i, j));

            p.open(i, j);
        }

        return 1.0 * x / (n * n);
    }

    public double mean()                      // sample mean of percolation
    // threshold
    {
        return StdStats.mean(results);
    }

    public double stddev()                    // sample standard deviation of
    // percolation threshold
    {
        return StdStats.stddev(results);
    }

    public double confidenceLo()              // low  endpoint of 95%
    // confidence interval
    {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    public double confidenceHi()              // high endpoint of 95%
    // confidence interval
    {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    public static void main(String[] args)    // test client (described below)
    {
        PercolationStats s = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        StdOut.printf("%-23s = %f\n", "mean", s.mean());
        StdOut.printf("%-23s = %f\n", "stddev", s.stddev());
        StdOut.printf("%-23s = %f, %f\n", "95% confidence interval", s
                .confidenceLo(), s.confidenceHi());
    }
}

