package com.parth.unionFind;

public class UF implements UnionFind {
	
	private int[] parent;
	
	private int[] rank;
	
	private int count;
	
	public UF(int N) {
		parent = new int[N];
		rank = new int[N];
		count = N;
		
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}

	public int count() {
		return count;
	}

	@Override
	public void union(int p, int q) {
		
		int rootP = find(p);
		int rootQ = find(q);
		
		if(rootP == rootQ) return;
		
		if(rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
		else if(parent[rootP] > parent[rootQ]) parent[rootQ] = rootP;
		else {
			parent[rootQ] = rootP;
			rank[rootQ]++;
		}
		count--;
	}

	@Override
	public int find(int p) {
		validate(p);
		while(p != parent[p]) {
			parent[p] = parent[parent[p]];
			p = parent[p];
		}
	return p;
	}

	public void validate(int p) {
		if (p < 0 || p >= parent.length) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (parent.length-1));
		}
	}
}
