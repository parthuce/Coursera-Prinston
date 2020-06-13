package com.parth.unionFind;

public class QuickUnionMax implements UnionFind{

	private int[] parent;
	
	private int[] rank;
	
	private int[] max;
	
	private int count;
	
	public QuickUnionMax(int N) {
		parent = new int[N];
		rank = new int[N];
        max = new int[N];
		count = N;
		
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			rank[i] = 0;
			max[i] = i;
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
		
		if(rank[rootP] < rank[rootQ]) { 
			parent[rootP] = rootQ;
			max[rootQ] = max[rootP] > max[rootQ] ? max[rootP] : max[rootQ];  
		}
		else if(parent[rootP] > parent[rootQ]) { 
			parent[rootQ] = rootP;
			max[rootP] = max[rootP] > max[rootQ] ? max[rootP] : max[rootQ];  
		}
		else {
			parent[rootQ] = rootP;
			max[rootP] = max[rootP] > max[rootQ] ? max[rootP] : max[rootQ];  
			rank[rootQ]++;
		}
		count--;
	}
	
	public int unionAndMax(int p, int q) {
		
		int rootP = find(p);
		int rootQ = find(q);
		
		if(rootP == rootQ) return max[rootP];
		
		if(rank[rootP] < rank[rootQ]) { 
			parent[rootP] = rootQ;
			max[rootQ] = max[rootP] > max[rootQ] ? max[rootP] : max[rootQ];  
			count--;
			return max[rootQ];
		}
		else if(parent[rootP] > parent[rootQ]) { 
			parent[rootQ] = rootP;
			max[rootP] = max[rootP] > max[rootQ] ? max[rootP] : max[rootQ]; 
			count--;
			return max[rootP];
		}
		else {
			parent[rootQ] = rootP;
			max[rootP] = max[rootP] > max[rootQ] ? max[rootP] : max[rootQ];  
			rank[rootQ]++;
			count--;
			return max[rootP];
		}
	}
	
	public int getMax(int p) {
		return max[find(p)];
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
	
	public int delete(int p) {
		if(p ==  parent.length-1) return p;
		return unionAndMax(p, p + 1);
	}

	public void validate(int p) {
		if (p < 0 || p >= parent.length) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (parent.length-1));
		}
	}
}
