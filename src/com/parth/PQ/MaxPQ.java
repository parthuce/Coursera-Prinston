package com.parth.PQ;

/**
 * Arr[(i-1)/2] Returns the parent node 
 * Arr[(2*i)+1] Returns the left child node
 * Arr[(2*i)+2] Returns the right child node
 */

public class MaxPQ<Key extends Comparable<Key>> {

	Key[] keys;
	int N;

	public MaxPQ(int capacity) {
		keys = (Key[]) new Comparable[capacity];
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public void insert(Key key) {
		keys[N++] = key;
		swim(N - 1);
	}

	public Key delMax() {
		if (isEmpty())
			return null;
		Key max = keys[0];
		swap(0, --N);
		sink(0);
		keys[N] = null; // prevent loitering
		return max;
	}

	private void swim(int k) {
		while (k > 0 && less((k - 1) / 2, k)) {
			swap(k, (k - 1) / 2);
			k = (k - 1) / 2;
		}
	}

	private boolean less(int i, int j) {
		return keys[i].compareTo(keys[j]) < 0;
	}

	private void swap(int k, int parent) {
		Key temp = keys[parent];
		keys[parent] = keys[k];
		keys[k] = temp;
	}

	private void sink(int k) {

		while (2 * k + 1 < N) {
			int j = 2 * k + 1;
			if (j < N - 1 && less(j, j + 1)) // Important to make it N - 1 here
				j++;
			if (!less(k, j))
				break;
			swap(k, j);
			k = j;
		}
	}

	public static void main(String[] args) {
		MaxPQ<Integer> heap = new MaxPQ<>(10);
		heap.insert(5);
		heap.insert(4);
		heap.insert(6);
		heap.insert(10);
		heap.insert(7);
		heap.insert(3);
		heap.insert(2);
		heap.insert(50);
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
		System.out.println(heap.delMax());
	}

}
