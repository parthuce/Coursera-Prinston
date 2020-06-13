package com.parth.unionFind;

public class QuickFind implements UnionFind {
	
	private int[] id;
	
	public QuickFind(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}

	@Override
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	@Override
	public void union(int p, int q) {
		for(int i = 0; i< id.length; i++) {
			if(id[p] == id[i]) {
				id[i] = id[q];
			}
		}
	}

	@Override
	public int find(int p) {
		return id[p];
	}

}
