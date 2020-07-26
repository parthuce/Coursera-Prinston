package com.parth.Graph;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

	private final Digraph G;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new IllegalArgumentException(" Digraph G is nnot valid");
		this.G = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		int[] result = shortest(v, w);
		return result[0];
	}

	// a common ancestor of v and w that participates in a shortest ancestral path;
	// -1 if no such path
	public int ancestor(int v, int w) {
		int[] result = shortest(v, w);
		return result[1];
	}

	// length of shortest ancestral path between any vertex in v and any vertex in
	// w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int[] result = shortest(v, w);
		return result[0];
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such
	// path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		int[] result = shortest(v, w);
		return result[1];
	}

	private int[] shortest(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		BreadthFirstDirectedPaths pathOfV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths pathOfW = new BreadthFirstDirectedPaths(G, w);
		return shortest(pathOfV, pathOfW);
	}

	private int[] shortest(Iterable<Integer> v, Iterable<Integer> w) {
		validateVertices(v);
		validateVertices(w);
		BreadthFirstDirectedPaths pathOfV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths pathOfW = new BreadthFirstDirectedPaths(G, w);
		return shortest(pathOfV, pathOfW);
	}

	private int[] shortest(BreadthFirstDirectedPaths bfsA, BreadthFirstDirectedPaths bfsB) {
		int ancestor = -1;
		int localLength = 0;
		int length = Integer.MAX_VALUE;
		for (int i = 0; i < G.V(); i++) {
			if (bfsA.hasPathTo(i) && bfsB.hasPathTo(i)) {
				localLength = bfsA.distTo(i) + bfsB.distTo(i);
				if (localLength < length) {
					ancestor = i;
					length = localLength;
				}
			}
		}

		int[] result = new int[2];
		if (ancestor == -1) {
			result[0] = -1;
			result[1] = ancestor;
		} else {
			result[0] = length;
			result[1] = ancestor;
		}
		return result;
	}

	private void validateVertex(int v) {
		if (v < 0 || v > G.V())
			throw new IllegalArgumentException(" Vertex must be between 0 and " + (G.V() - 1));
	}

	private void validateVertices(Iterable<Integer> vertices) {
		if (vertices == null) {
			throw new IllegalArgumentException("argument is null");
		}
		for (int v : vertices) {
			validateVertex(v);
		}
	}

	// do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}
}
