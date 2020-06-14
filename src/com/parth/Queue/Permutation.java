package com.parth.Queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

	public static void main(String[] args) {
		
		final int k = Integer.parseInt(args[0]);
		
		RandomizedQueue<String> queue = new RandomizedQueue<>();
		
		String[] tokens = StdIn.readAllStrings();
		for (String token : tokens) {
			queue.enqueue(token);
		}
		
		for (int i = 0; i < k; i++) {
			StdOut.println(queue.dequeue());
		}
	}

}
