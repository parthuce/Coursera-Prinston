package com.parth.MST;

class Edge implements Comparable<Edge> {
	private final int v;
	private final int w;
	private final double weight;
	
	Edge(int v, int w, double weight) {
		if (v < 0 || w < 0) throw new IllegalArgumentException("vertex must be non-negative.");
		if (Double.isNaN(weight)) throw new IllegalArgumentException("weight is Nan.");
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	public int either() {
		return v;
	}
	
	public int other(int v) {
		if (this.v == v) {
			return w;
		} else if (this.w == v) {
			return this.v;
		} else {
			throw new IllegalArgumentException("Illegal endpoint.");
		}
	}
	
	public double weight() {
		return weight;
	}
	
	public int compareTo(Edge that) {
		if (this.weight > that.weight) return 1;
		if (this.weight < that.weight) return -1;
		return 0;
		//Double.compare(this.weight, that.weight);
	}
	
	public String toString() {
		return String.format("%d-%d %.5f", v, w, weight); //Learning Opportunity
	}
	
	/* Unit test case */
	public static void main(String args[]) {
		Edge edge = new Edge(5, 10, 5.67);
		System.out.println(edge);
		//System.out.println(edge.other(edge.either()));
		
	}
}