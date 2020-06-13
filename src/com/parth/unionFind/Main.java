package com.parth.unionFind;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Main {

	public static void main(String[] args) {
		StdOut.println("Union Find Program Starts From Here!");
		int N = StdIn.readInt();
		UnionFind uf = new QuickFind(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q)) {
				uf.union(p, q);
				StdOut.println(p + " " + q);
			}
		}
	}
}
