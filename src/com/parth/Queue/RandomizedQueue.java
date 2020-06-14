package com.parth.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int size = 0;
	private Item[] randomQueue;

	// construct an empty randomized queue
	public RandomizedQueue() {
		randomQueue = (Item[]) new Object[1];
	}

	// is the randomized queue empty?
	public boolean isEmpty() {
		return (size == 0);
	}

	// return the number of items on the randomized queue
	public int size() {
		return size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item connot be null.");
		}
		randomQueue[size++] = item;
		if (size == randomQueue.length) {
			resizeArray(randomQueue.length * 2);
		}
	}

	private void resizeArray(int newCapacity) {
		Item[] newArray = (Item[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newArray[i] = randomQueue[i];
		}
		randomQueue = newArray;
	}

	// remove and return a random item
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("RandomizedQueue is empty.");
		}
		int i = StdRandom.uniform(size);
		Item item = randomQueue[i];
		randomQueue[i] = randomQueue[--size];
		randomQueue[size] = null;
		if (size() > 0 && size() == randomQueue.length / 4) {
			resizeArray(randomQueue.length / 2);
		}
		return item;
	}

	// return a random item (but do not remove it)
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("RandomizedQueue is empty.");
		}
		return randomQueue[StdRandom.uniform(size)];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedIterator();
	}

	/**
	 * Serialization of the queue.
	 *
	 * TODO remove this method before your submission.
	 */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			result += "," + randomQueue[i];
		}
		if (!result.isEmpty()) {
			result = result.substring(1);
		}
		return "[" + result + "]";
	}

	private class RandomizedIterator implements Iterator<Item> {

		private final int[] indices = new int[size];
		private int counter = 0;

		RandomizedIterator() {
			for (int i = 0; i < size; i++) {
				indices[i] = i;
			}
			StdRandom.shuffle(indices);
		}

		@Override
		public boolean hasNext() {
			return counter < indices.length;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("No more items");
			}
			return randomQueue[indices[counter++]];
		}

	}

	// unit testing (required)
	public static void main(String[] args) {
		StdOut.println("Tests start.");

		// Test 1: public opeations
		RandomizedQueue<Integer> q1 = new RandomizedQueue<>();
		StdOut.println("Test 1A passed? " + q1.isEmpty());
		StdOut.println("Test 1B passed? " + q1.toString().equals("[]"));

		q1.enqueue(1);
		q1.enqueue(2);
		StdOut.println("Test 1C passed? " + q1.toString().equals("[1,2]"));
		StdOut.println("Test 1D passed? " + (q1.size() == 2));
		int test1E = q1.iterator().next();
		StdOut.println("Test 1E passed? " + (test1E == 1 || test1E == 2));
		q1.enqueue(3);
		q1.enqueue(4);
		StdOut.println("Test 1F passed? " + q1.toString().equals("[1,2,3,4]"));
		q1.dequeue();
		String test1G = q1.toString();
		StdOut.println("Test 1G passed? " + (test1G.equals("[4,2,3]") || test1G.equals("[1,4,3]")
				|| test1G.equals("[1,2,4]") || test1G.equals("[1,2,3]")));
		q1.dequeue();
		q1.dequeue();
		// Queue should be resized when 25% full: the size will be reduced by
		// 50%. However, the unused elements in the new array are not visible
		// because they're excluded by the {@code toString()} method.
		String test1H = q1.toString();
		StdOut.println("Test 1H passed? "
				+ (test1H.equals("[1]") || test1H.equals("[2]") || test1H.equals("[3]") || test1H.equals("[4]")));
		q1.dequeue();
		StdOut.println("Test 1I passed? " + q1.toString().equals("[]"));
		StdOut.println("Test 1J passed? " + q1.isEmpty());
		StdOut.println("Test 1K passed? " + !q1.iterator().hasNext());
		StdOut.println("Test 1L passed? " + (q1.iterator() != q1.iterator()));
		q1.enqueue(1);
		StdOut.println("Test 1M passed? " + q1.toString().equals("[1]"));
		q1.enqueue(2);
		StdOut.println("Test 1N passed? " + q1.toString().equals("[1,2]"));

		// Test 2: exceptions
		RandomizedQueue<Integer> q2 = new RandomizedQueue<>();
		try {
			q2.dequeue();
			StdOut.println("Test 2A passed? " + false);
		} catch (Exception e) {
			boolean result = e instanceof NoSuchElementException;
			StdOut.println("Test 2A passed? " + result);
		}
		try {
			q2.sample();
			StdOut.println("Test 2B passed? " + false);
		} catch (Exception e) {
			boolean result = e instanceof NoSuchElementException;
			StdOut.println("Test 2B passed? " + result);
		}
		try {
			q2.enqueue(null);
			StdOut.println("Test 2C passed? " + false);
		} catch (Exception e) {
			boolean result = e instanceof IllegalArgumentException;
			StdOut.println("Test 2C passed? " + result);
		}
		try {
			q2.iterator().remove();
			StdOut.println("Test 2D passed? " + false);
		} catch (Exception e) {
			boolean result = e instanceof UnsupportedOperationException;
			StdOut.println("Test 2D passed? " + result);
		}
		try {
			q2.iterator().next();
			StdOut.println("Test 2E passed? " + false);
		} catch (Exception e) {
			boolean result = e instanceof NoSuchElementException;
			StdOut.println("Test 2E passed? " + result);
		}

		// Test 3: types
		RandomizedQueue<String> q3A = new RandomizedQueue<>();
		q3A.enqueue("Hello Algorithm");
		StdOut.println("Test 3A passed? " + true);
		RandomizedQueue<Double> q3B = new RandomizedQueue<>();
		q3B.enqueue(3.1415926);
		StdOut.println("Test 3B passed? " + true);

		StdOut.println("Tests finished.");
	}

}
