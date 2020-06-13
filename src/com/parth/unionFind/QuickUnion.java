package com.parth.unionFind;

public class QuickUnion implements UnionFind{
	
	private int[] id;
	
	public QuickUnion(int N) {
		id = new int[N];
		for (int i=0; i < N; i++) {
			id[i] = i;
		}
	}

	@Override
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	@Override
	public void union(int p, int q) {
		id[find(p)] = find(q);
		
	}

	@Override
	public int find(int p) {
		while (p != id[p]) 
			p = id[p];
		return p;
	}
}
