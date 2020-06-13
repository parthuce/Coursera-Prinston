package com.parth.unionFind;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
	
	private final int N;
	private final WeightedQuickUnionUF uf;
	private boolean[] isOpen;
	private int openCount = 0;
	

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	if (n <= 0) {
    		throw new IllegalArgumentException("Grid dimension must be greater than zero.");
    	}
    	N = n;
    	uf = new WeightedQuickUnionUF(n * n + 2); // Top and Bottom element
    	isOpen = new boolean [n * n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	validate(row, col);
    	if (isOpen(row, col)) return;
    	int index = convertTo1D(row, col);
    	
    	// Union with open neighbor
    	if (row > 1 && isOpen(row - 1, col)) 
    		uf.union(index, convertTo1D(row - 1, col));
    	
    	if (row < N && isOpen(row + 1, col)) 
    		uf.union(index, convertTo1D( row + 1, col));
    	    	
    	if (col > 1 && isOpen(row, col - 1))
    		uf.union(index, convertTo1D(row, col - 1));
    	
    	if (col < N && isOpen(row, col + 1)) 
    		uf.union(index, convertTo1D(row, col + 1));

    	//Union with Top element
    	if (row == 1)
    		uf.union(N * N, index);
    	
    	//Union with Bottom element
    	if (row == N) 
    		uf.union(N * N + 1 , index);
    	
    	isOpen[index] = true;
    	openCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	validate(row, col);
		return isOpen[convertTo1D(row, col)];
    	
    }

    // is the site (row, col) full?
	public boolean isFull(int row, int col) {
		validate(row, col);
		return uf.find(N * N) == uf.find(convertTo1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
		return openCount;
    	
    }

    // does the system percolate?
    public boolean percolates() {
		return uf.find(N * N) == uf.find(N * N + 1);    	
    }
    
    // Covert 2D grid into 1D Union Find size element
    private int convertTo1D(int row, int col) {
    	return (row - 1) * N + (col - 1);
    }
    
    //Verify the corner case
    private void validate(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N)
            throw new IllegalArgumentException("Site coordinate must be greater than zero and less than " + N);
    }
    
    // test client (optional)
    public static void main(String[] args) {
    	
    }
}
