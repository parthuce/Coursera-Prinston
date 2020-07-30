package com.parth.MST;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

class EdgeWeightedGraph {
	
	private List<Edge>[] adj;
	private int E = 0;
	
	EdgeWeightedGraph(int V) {
		if (V < 0) throw new IllegalArgumentException("No of vertices must be non-negative");
		adj = (List<Edge>[]) new LinkedList[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new LinkedList<Edge>();
		}
	}
	
	public void addEdge(Edge edge) {
		int v = edge.either();
		int w = edge.other(v);
		validateVertex(v);
		validateVertex(w);
		adj[v].add(edge);
		adj[w].add(edge);
		E++;
	}
	
	public Iterable<Edge> adj(int v) {
		validateVertex(v);
		return adj[v];
	}
	
	public Iterable<Edge> edges() {
		Set<Edge> edges = new HashSet<Edge>(E);
		for (int i = 0; i < adj.length; i++) {
			edges.addAll(adj[i]);
		}
		return edges;
	}
	
	public Iterable<Edge> edges2() {
		List<Edge> edges = new LinkedList<>();
		for (int i = 0; i < adj.length; i++) {
			int selfloop = 0;
			for (Edge e : adj[i]) {
				if (e.other(i) > i) {
					edges.add(e);
				}
				//We will add only one copy of self loop.
				else if (e.other(i) == i) {
					if (selfloop % 2 == 0) edges.add(e);
					selfloop++;
				}
			}
		}
		return edges;
	}
	
	public Iterable<Edge> edges3() {
	        List<Edge> list = new LinkedList<>();
	        for (int v = 0; v < adj.length; v++) {
	            int selfLoops = 0;
	            for (Edge e : adj(v)) {
	            	//System.out.println(v);
					//System.out.println(e.other(v));
	                if (e.other(v) > v) {
	                    list.add(e);
	                }
	                // add only one copy of each self loop (self loops will be consecutive)
	                else if (e.other(v) == v) {
	                    if (selfLoops % 2 == 0) list.add(e);
	                    selfLoops++;
	                }
	            }
	        }
	        return list;
	    }
	
	public int degree(int v) {
		validateVertex(v);
	    return adj[v].size();
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
	    s.append(adj.length + " " + E + '\n');
	    for (int v = 0; v < adj.length; v++) {
	       s.append(v + ": ");
	       for (Edge e : adj[v]) {
			   s.append(e + "  ");
	       }
	       s.append('\n');
	    }
	    return s.toString();
	}
	
	public int V() {
		return adj.length;
	}
	
	public int E() {
		return E;
	}
	
	/* Private Helper Method */
	private void validateVertex(int v) {
		int V = adj.length;
		if (v < 0 || v >= V) throw new IllegalArgumentException("Vertex must be between 0 and " + (V - 1));
	}
	
	public static void main(String args[]) {
		EdgeWeightedGraph G = new EdgeWeightedGraph(5);
		Edge e = new Edge(0, 1, 0.55);
		G.addEdge(e);
		//System.out.println(e.either());
		System.out.println(e.other(1));
		//System.out.println(G);
		for (Edge edge : G.edges3()) {
			System.out.println(edge);
		}
	}
}