package com.parth.SP;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private Picture picture;
	private double[][] energy;
	private boolean isTransposed;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		this.picture = new Picture(picture);
		energy = new double[picture.width()][picture.height()];
	}

	// current picture
	public Picture picture() {
		return new Picture(picture);

	}

	// width of current picture
	public int width() {
		return picture.width();

	}

	// height of current picture
	public int height() {
		return picture.height();

	}

	// energy of pixel at column x and row y
	public double energy(int x, int y) {
		validateColumnIndex(x);
		validateRowIndex(y);
		
		if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
			return energy[x][y] = 1000.0;
		}
		
		return energy[x][y] = Math.sqrt((xGradiant(x,y) + yGradiant(x,y)));
	}
	
	private void validateRowIndex(int row) {
        if (row < 0 || row >= height())
            throw new IllegalArgumentException("row index must be between 0 and " + (height() - 1) + ": " + row);
    }

    private void validateColumnIndex(int col) {
        if (col < 0 || col >= width())
            throw new IllegalArgumentException("column index must be between 0 and " + (width() - 1) + ": " + col);
    }

	private double xGradiant(int col, int row) {
        int rgbRight = picture.getRGB(col + 1, row);
        int rgbLeft = picture.getRGB(col - 1, row);
        int r = ((rgbRight >> 16) & 0xFF) - ((rgbLeft >> 16) & 0xFF);
        int g = ((rgbRight >>  8) & 0xFF) - ((rgbLeft >>  8) & 0xFF);
        int b = ((rgbRight >>  0) & 0xFF) - ((rgbLeft >>  0) & 0xFF); 
        return (Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2));
	}
	
	private double yGradiant(int col, int row) {
		int rgbTop = picture.getRGB(col, row + 1);
        int rgbButtom = picture.getRGB(col, row - 1);
        int r = ((rgbTop >> 16) & 0xFF) - ((rgbButtom >> 16) & 0xFF);
        int g = ((rgbTop >>  8) & 0xFF) - ((rgbButtom >>  8) & 0xFF);
        int b = ((rgbTop >>  0) & 0xFF) - ((rgbButtom >>  0) & 0xFF); 
        return (Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2));
	}

	// sequence of indices for horizontal seam
	public int[] findHorizontalSeam() {		
		if (!isTransposed) {
			transpose();
		}		
		int[] seam = findVerticalSeam();
		transpose();
		return seam;
	}

	// sequence of indices for vertical seam
	public int[] findVerticalSeam() {
		
		double[][] energy = new double[width()][height()];
		double[][] distTo = new double[width()][height()];
		int[][] edgeTo = new int[width()][height()];
		
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				energy[col][row] = energy(col, row);
				distTo[col][row] = Double.POSITIVE_INFINITY;
			}
			distTo[col][0] = 0.0;
		}
		
		for (int row = 0; row < height() - 1; row++) {
			for (int col = 0; col < width(); col++) {
				if (col > 0) {
					relax(col, row, col - 1 ,row + 1, distTo, energy, edgeTo);
				}
				
				relax(col, row, col ,row + 1, distTo, energy, edgeTo);
				
				if (col < width() - 1) {
					relax(col, row, col + 1 ,row + 1, distTo, energy, edgeTo);
				}
			}
		}
		
		double minDist = Double.POSITIVE_INFINITY;
		int minIndex = Integer.MIN_VALUE;
		for (int col = 0; col < width(); col++) {
			if (minDist > distTo[col][height() - 1]) {
				minDist = distTo[col][height() - 1];
				minIndex = col;
			}
		}
		
		int[] seam = new int[height()];
		
		for (int row = height() - 1; row >= 0; row--) {
			seam[row] = minIndex;
			minIndex = edgeTo[minIndex][row]; // Very important.
		}
		
		return seam;

	}
	
	private void relax(int col1, int row1, int col2, int row2, double[][] distTo, double[][] energy, int[][] edgeTo) {
		if (distTo[col2][row2] > distTo[col1][row1] + energy[col2][row2]) {
			distTo[col2][row2] = distTo[col1][row1] + energy[col2][row2];
			edgeTo[col2][row2] = col1;
		}
	}
	
	private void transpose() {
		Picture transposed = new Picture(height(), width()); //flip height and width
		double[][] energy = new double[height()][width()];
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				transposed.setRGB(row, col, picture.getRGB(col, row));
				energy[row][col] = this.energy[col][row]; 
			}
		}
		picture = transposed;
		this.energy = energy;
		isTransposed = !isTransposed;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		if (!isTransposed) {
			transpose();
		}		
		removeVerticalSeam(seam);
		transpose();
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		Picture picture = new Picture(width() - 1, height());
		
		int[][] image = new int[height()][width()];
		int[][] destImage = new int[height()][width() - 1];
		
		for (int row = 0; row < height(); row++) {
			for (int col = 0; col < width(); col++) {
				image[row][col] = this.picture.getRGB(col, row);
			}
		}
				
		for (int row = 0; row < height(); row++) {
			int index = seam[row];
			System.arraycopy(image[row], 0, destImage[row], 0, index );
			System.arraycopy(image[row], index + 1, destImage[row], index, (width() - index - 1) );
		}
		
		for (int row = 0; row < height(); row++) {
			for (int col = 0; col < width() - 1; col++) {
				picture.setRGB(col, row, destImage[row][col]);
			}
		}
		
		this.picture = picture;
	}
	
	// unit testing (optional)
	public static void main(String[] args) {
        
	}
}
