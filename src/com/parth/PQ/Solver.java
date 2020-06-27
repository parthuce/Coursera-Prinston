package com.parth.PQ;

import java.util.Deque;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private MinPQ<SearchNode> pq;
	private SearchNode solutionNode;
	private boolean isSolvable;

	private class SearchNode implements Comparable<SearchNode> {
		private final SearchNode previous;
		private final Board current;
		private final int moves;
		private int priority = -1;

		SearchNode(Board current, SearchNode previous, int moves) {
			this.current = current;
			this.previous = previous;
			this.moves = moves;
		}

		private int hummingPriority() {
			if (priority == -1)
				priority = moves + current.hamming();
			return priority;
		}

		private int manhattanPriority() {
			if (priority == -1)
				priority = moves + current.manhattan();
			return priority;
		}

		@Override
		public int compareTo(SearchNode that) {
			return this.manhattanPriority() - that.manhattanPriority();
		}
	}

	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		if (initial == null) {
			throw new IllegalArgumentException("Initial board can't be a null");
		}

		pq = new MinPQ<>();
		solutionNode = null;
		pq.insert(new SearchNode(initial, null, 0));

		while (!pq.isEmpty()) {
			SearchNode node = pq.delMin();
			if (node.current.isGoal()) {
				solutionNode = node;
				isSolvable = true;
				break;
			}
			for (Board board : node.current.neighbors()) {
				if (node.previous != null && board.equals(node.previous.current))
					continue;
				pq.insert(new SearchNode(board, node, node.moves + 1));
			}
		}
	}

	// is the initial board solvable? (see below)
	public boolean isSolvable() {
		return isSolvable;

	}

	// min number of moves to solve initial board; -1 if unsolvable
	public int moves() {
		return isSolvable ? solutionNode.moves : -1;

	}

	// sequence of boards in a shortest solution; null if unsolvable
	public Iterable<Board> solution() {
		if (!isSolvable)
			return null;
		Deque<Board> solution = new LinkedList<>();
		SearchNode node = solutionNode;
		while (node != null) {
			solution.addFirst(node.current);
			node = node.previous;
		}
		return solution;

	}

	// test client (see below)
	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] tiles = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				tiles[i][j] = in.readInt();
		Board initial = new Board(tiles);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
