package com.parth.PQ;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {

	private int[][] tiles;

	private final int n;

	// create a board from an n-by-n array of tiles,
	// where tiles[row][col] = tile at (row, col)
	public Board(int[][] tiles) {
		n = tiles.length;
		this.tiles = clone(tiles);
	}

	// string representation of this board
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	// board dimension n
	public int dimension() {
		return n;

	}

	// number of tiles out of place
	public int hamming() {
		int hd = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tiles[i][j] == 0) {
					continue;
				}
				if (tiles[i][j] != n * i + j + 1)
					hd++;
			}
		}
		return hd;
	}

	// sum of Manhattan distances between tiles and goal
	public int manhattan() {
		int md = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int value = tiles[i][j];
				if (value == 0)
					continue;
				value--;
				md += Math.abs((value / n) - i) + Math.abs((value % n) - j);
			}
		}
		return md;
	}

	// is this board the goal board?
	public boolean isGoal() {
		return hamming() == 0;

	}

	// does this board equal y?
	public boolean equals(Object y) {
		return Arrays.deepEquals(tiles, ((Board) y).tiles);
	}

	// all neighboring boards
	public Iterable<Board> neighbors() {

		List<Board> neighbors = new LinkedList<>();
		int row = 0, col = 0;
		// row = col = -1;
		
		outerloop:
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tiles[i][j] == 0) {
					row = i;
					col = j;
					break outerloop;
				}
			}
		}

		if (row > 0) {
			int[][] left = clone(tiles);
			swap(left, row, col, row - 1, col);
			neighbors.add(new Board(left));
		}

		if (row < n - 1) {
			int[][] right = clone(tiles);
			swap(right, row, col, row + 1, col);
			neighbors.add(new Board(right));
		}

		if (col > 0) {
			int[][] top = clone(tiles);
			swap(top, row, col, row, col - 1);
			neighbors.add(new Board(top));
		}

		if (col < n - 1) {
			int[][] buttom = clone(tiles);
			swap(buttom, row, col, row, col + 1);
			neighbors.add(new Board(buttom));
		}

		return neighbors;

	}

	private void swap(int[][] tiles, int row, int col, int newRow, int newCol) {
		int temp = tiles[row][col];
		tiles[row][col] = tiles[newRow][newCol];
		tiles[newRow][newCol] = temp;
	}

	private int[][] clone(int[][] tiles) {
		int[][] copy = new int[tiles.length][];
		for (int row = 0; row < tiles.length; row++) {
			copy[row] = tiles[row].clone();
		}
		return copy;
	}

	// a board that is obtained by exchanging any pair of tiles
	public Board twin() {
		
		int row = 0;
		outerloop:
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tiles[i][j] == 0) {
					row = i;
					break outerloop;
				}
			}
		}
		
		int[][] twin = clone(tiles);
        if (row != 0) {
            swap(twin, 0, 0, 0, 1);
        } else {
            swap(twin, 1, 0, 1, 1);
        }
        return new Board(twin);
	}

	// unit testing (not graded)
	public static void main(String[] args) {

	}
}
