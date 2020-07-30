package com.parth.Graph;

import java.util.PriorityQueue;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class Test {
	
	public static void main(String[] args) {
		
		EdgeWeightedGraph G = new EdgeWeightedGraph(5);
		Edge e = new Edge(0, 1, 0.55);
		G.addEdge(e);
		System.out.println(e.either());
		System.out.println(e.other(e.either()));
		//System.out.println(G);
		for (Edge edge : G.edges()) {
			System.out.println(edge);
		}
		
		 PriorityQueue<Integer> pq= new PriorityQueue<Integer>();
		 pq.offer(1);
		 pq.poll();
		 pq.isEmpty();
		 
		 System.out.println(Math.pow(0, 2));
	}
	

}
