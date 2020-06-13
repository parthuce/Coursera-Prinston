package com.parth.unionFind;

public interface UnionFind {
	
	default boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public void union(int p, int q);
	
	public int find(int p);

}
