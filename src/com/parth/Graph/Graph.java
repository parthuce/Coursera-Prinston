package com.parth.Graph;

import edu.princeton.cs.algs4.Bag;

public class Graph {
	
	private int E = 0;
	private Bag<Integer>[] adj;

	Graph(int V) {
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
		adj = new Bag[V];
		for (int i = 0; i < adj.length; i++) {
			adj[i] = new Bag<Integer>();
		}
	}
	
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		adj[v].add(w);
		adj[w].add(v);
		E++;
	}
	
	private void validateVertex(int v) {
		if (v < 0 || v >= adj.length) {
			throw new IllegalArgumentException("vertex must be in range of 0 and " + adj.length);
		}
	}
	
	public int V() {
		return adj.length;
	}
	
	public int E() {
		return E;
	}
	
	public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
	
	public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(adj.length + " vertices, " + E + " edges " + "\n");
        for (int v = 0; v < adj.length; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

	
	Iterable<Integer> adj(int v) {
		return adj[v];
	}
	
	public static void main(String[] args) {
		Graph G = new Graph(4);
		G.addEdge(0, 1);
		G.addEdge(0, 2);
		G.addEdge(2, 3);
		G.addEdge(1, 3);
		
		System.out.println(G.toString());
	}
}
