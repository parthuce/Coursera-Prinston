package com.parth.trie;

import edu.princeton.cs.algs4.TrieST;

public class BoggleSolver {

	private TrieST<String> trie;
	private final int R = 26;

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {

		for (String word : dictionary)
			trie.put(word, word);

	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		boolean[][] marked = new boolean[board.rows()][board.cols()];
		for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.cols(); j++) {
				char c = board.getLetter(i, j);
				marked[i][j] = true;
			}
		}

		return null;

	}

	private char[] getAdj(int i, int j, BoggleBoard board) {
		char[] adj = new char[8];
		
		/*board.getLetter(i - 1, j - 1);
		board.getLetter(i - 1, j);
		board.getLetter(i - 1, j + 1);
		
		board.getLetter(i, j - 1);
		board.getLetter(i, j + 1);
		
		board.getLetter(i + 1, j - 1);
		board.getLetter(i + 1, j);
		board.getLetter(i + 1, j + 1);*/
		
		if (i > 0) {
			if (j > 0) {
				board.getLetter(i - 1, j - 1);
			} 
			if (j < board.cols() - 1) {
				board.getLetter(i - 1, j + 1);
			} 
			board.getLetter(i - 1, j);
		}
		
		if (i < board.rows() - 1) {
			board.getLetter(i + 1, j);
			if (j > 0) {
				board.getLetter(i + 1, j - 1);
			}
			if (j < board.cols() - 1) {
				board.getLetter(i + 1, j + 1);
			}
		}
		
		if (j > 0) {
			board.getLetter(i, j - 1);
		}
		if (j < board.cols() - 1) {
			board.getLetter(i, j + 1);
		}
		


		// TODO Auto-generated method stub
		return null;
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		return 0;
	}
}
