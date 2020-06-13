package com.parth.unionFind;

public class WeightedQuickUnion implements UnionFind {
	
	private int[] id;
	
	private int[] size;
	
	private int count;
	
	public WeightedQuickUnion(int N) {
		id = new int[N];
		size = new int[N];
		count = N;
		
		for(int i = 0; i < N; i++) {
			id[i] = i;
			size[i] = 1; 
		}
	}

	@Override
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public int count() {
		return count;
	}

	@Override
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		
		if(rootP == rootQ) return;
		
		if(size[rootP] < size[rootQ]){	
			id[rootP] = rootQ;
			size[rootQ] += size[rootP];
		} else { 
			id[rootQ] = rootP;
			size[rootP] += size[rootQ];
		}
		count--;
	}

	@Override
	public int find(int p) {
		validate(p);
		while (p != id[p]) {
			p = id[p];
		}
		return p;
	}
	
	public void validate(int p) {
		if (p < 0 || p >= id.length) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (id.length-1));
		}
	}
}
