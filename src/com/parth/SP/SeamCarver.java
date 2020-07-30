package com.parth.SP;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

	private Picture picture;

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null) throw new IllegalArgumentException("Picture is null");
		this.picture = new Picture(picture);
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
			return 1000.0;
		}
		
		return Math.sqrt((xGradiant(x, y) + yGradiant(x, y)));
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
		transpose();
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
		for (int col = 0; col < width(); col++) {
			for (int row = 0; row < height(); row++) {
				transposed.setRGB(row, col, picture.getRGB(col, row));
			}
		}
		picture = transposed;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		transpose();
		removeVerticalSeam(seam);
		transpose();
	}

	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		validateSeam(seam);
		Picture picture = new Picture(width() - 1, height());
		
		for (int row = 0; row < height(); row++) {
			int index = seam[row];
			for (int col = 0; col < width() - 1; col++) {
				if (col < index) {
					picture.setRGB(col, row, this.picture.getRGB(col, row));

				} else {
					picture.setRGB(col, row, this.picture.getRGB(col + 1, row));

				}
			}
		}
		this.picture = picture;
	}
	
	private void validateSeam(int[] seam) {
		if (seam == null) throw new IllegalArgumentException("Seam is null");
		if (!isValidSeam(seam) || width() <= 1) throw new IllegalArgumentException("Seam is not valid");
	}
	
	private boolean isValidSeam(int[] seam) {
        if (seam.length != height()) {
            return false;
        }
        for (int i = 0; i < seam.length; i++) {
            int entry = seam[i];
            if (entry < 0 || entry > width() - 1) {
                return false;
            }
            if (i != seam.length - 1) {
                if (Math.abs(entry - seam[i + 1]) > 1) {
                    return false;
                }
            }
        }
        return true;
    }

	// unit testing (optional)
	public static void main(String[] args) {
        
	}
}
