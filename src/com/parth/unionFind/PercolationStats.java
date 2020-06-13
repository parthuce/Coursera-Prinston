package com.parth.unionFind;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private final int trials;
	private double[] thresholds;
	
	private final float CONFIDENCE = 1.96f;
	

	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	if (n <=0 || trials <= 0) {
    		throw new IllegalArgumentException("Grid dimension and #trials must be greater than zero.");
    	}
    	
    	thresholds = new double[trials];
    	this.trials = trials;
    	
    	for (int i = 0; i < trials; i++) {
    		Percolation percolation = new Percolation(n);
			while(!percolation.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				percolation.open(row, col);
			}
			thresholds[i] = percolation.numberOfOpenSites() / (double) (n * n);
		}
    }

    // sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
		return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE * stddev()) / Math.sqrt(trials));
    }

   // test client (see below)
   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);

       PercolationStats stats = new PercolationStats(n, trials);
       
       System.out.println("mean:\t\t\t\t = " + stats.mean());
       System.out.println("stddev:\t\t\t\t = " + stats.stddev());
       System.out.println("95% confidence interval:\t = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
