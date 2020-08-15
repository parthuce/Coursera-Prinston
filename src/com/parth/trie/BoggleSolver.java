package com.parth.trie;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

public class BoggleSolver {

	private TST<String> trie;

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		if (dictionary == null) throw new IllegalArgumentException("dictionary must be valid.");
		trie = new TST<>();
		for (String word : dictionary)
			trie.put(word, word);

	}

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		if (board == null) throw new IllegalArgumentException("board must be valid.");
		Set<String> words = new HashSet<>();
		for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.cols(); j++) {
				boolean[][] marked = new boolean[board.rows()][board.cols()];
				StringBuilder str = new StringBuilder();
				char c = board.getLetter(i, j);
				str.append(c); if (c == 'Q') str.append('U');
				dfs(i, j, marked, board, words, str);
			}
		}
		return words;
	}

	private void dfs(int i, int j,boolean[][] marked,BoggleBoard board, Set<String> words, StringBuilder str) {
		marked[i][j] = true;
		for (int row = i - 1; row <= i + 1; row++) {
			for (int col = j - 1; col <= j + 1; col++) {
				if (isSafe(row, col, board) && !marked[row][col]) {
					char c = board.getLetter(row, col);
					str.append(c); 
					if (c == 'Q') 
						str.append('U');
					if (str.length() > 2) {
						int count = 0;
						String string = str.toString();
						for (String prefix : trie.keysWithPrefix(string)){
							count++;
							if (string.equals(prefix) && trie.contains(string)) words.add(string);
						}
						if (count == 0) {
							if (c == 'Q') str.deleteCharAt(str.length() - 1);
							str.deleteCharAt(str.length() - 1);
							continue;
						}
					}
					dfs(row, col, marked, board, words, str);
					if (c == 'Q') str.deleteCharAt(str.length() - 1);
					str.deleteCharAt(str.length() - 1);
				}
			}
		}
		marked[i][j] = false;
	}

	private boolean isSafe(int row, int col, BoggleBoard board) {
		if (row < 0 || row >= board.rows() || col < 0 || col >= board.cols()) {
			return false;
		}
		return true;
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through Z.)
	public int scoreOf(String word) {
		if (word == null) throw new IllegalArgumentException("word must be valid.");
		if (trie.contains(word)) {
			int length = word.length();
			if (length < 5) return 1;
			if (length == 5) return 2;
			if (length == 6) return 3;
			if (length == 7) return 5;
			if (length >= 8) return 11;
		}
		return 0;
	}
	
	public static void main(String[] args) {
	    In in = new In(args[0]);
	    String[] dictionary = in.readAllStrings();
	    BoggleSolver solver = new BoggleSolver(dictionary);
	    BoggleBoard board = new BoggleBoard(args[1]);
	    int score = 0;
	    for (String word : solver.getAllValidWords(board)) {
	        StdOut.println(word);
	        score += solver.scoreOf(word);
	    }
	    StdOut.println("Score = " + score);
	}
}
