package com.parth.BST;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.princeton.cs.algs4.StdOut;

public class BinarySearchST <K extends Comparable<K>, V> {

	K[] keys;
	V[] values;
	int size = 0;
	
	public BinarySearchST() {
		keys = (K[]) new Comparable[1]; //Important
		values = (V[]) new Object[1];
	}
	
	public void put(K key, V value) {
		if (key == null) throw new IllegalArgumentException("Key Can't be null");
		if(value == null) {
			delete(key);
		}
		int index = rank(key);
		if(index < size && key.compareTo(keys[index]) == 0) {
			values[index] = value;
			return;
		}
		
		for (int j = size - 1; j >= index; j--) {
			keys[j + 1] = keys[j];
			values[j + 1] = values[j];
		}
		keys[index] = key;
		values[index] = value;
		size++;
		
		if (size == values.length) resize(size * 2);
	}
	
	private void resize(int newSize) {
		K[] newKeys = (K[]) new Comparable[newSize];
		V[] newValues = (V[]) new Object[newSize];
		
		for (int i = 0; i < size; i++) {
			newKeys[i] = keys[i];
			newValues[i] = values[i];
		}
		keys = newKeys;
		values = newValues;
	}
	
	public V get(K key) {
		if (key == null) throw new IllegalArgumentException("Key Can't be null");
		if (isEmpty()) return null;
		int index = rank(key);
		if(index < size && key.compareTo(keys[index]) == 0) {
			return values[index];
		}
		return null;
	}
	
	public void delete(K key) {
		if (key == null) throw new IllegalArgumentException("Key Can't be null");
		int index = rank(key);
		if(index < size - 1 && key.compareTo(keys[index]) == 0) {
			while (index < size) {
				keys[index] = keys[index + 1];
				values[index] = values[index + 1];
				index++;
			}
			if(size > 0 && size == keys.length/4) resize(keys.length/2);
			keys[size - 1] = null; 
			values[size - 1] = null;
			size--;
		}
	}
	
	public int rank(K key) {
		if (key == null) throw new IllegalArgumentException("Key Can't be null");
		int low = 0, high = size - 1;
		while (low <= high) {
			int middle = low + (high - low) / 2;
			if(keys[middle].compareTo(key) == 0) {
				return middle;
			} else if (keys[middle].compareTo(key) > 0) {
				high = middle - 1;
			} else {
				low = middle + 1;
			}
		}
		return low; // gives a best probable location of key.
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	Iterable<K> keys(){
		Queue<K> queue = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			queue.add(keys[i]);
		}
		return queue;
	}
	
	/** Ordered symbol table methods */
	
	public K min() {
		if (isEmpty()) throw new NoSuchElementException("Called min() with empty symbol table");
		return keys[0];
	}
	
	public K max() {
		if (isEmpty()) throw new NoSuchElementException("Called max() with empty symbol table");
		return keys[size - 1];
	}
	
	public K select(int k) {
		if (k < 0 || k >= size) throw new IllegalArgumentException("Called select with invalid argument: " + k);
		return keys[k];
	}
	
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
		delete(min());
	}
	
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
		delete(max());
	}
	
	public K ceiling(K key) {
		if (key == null) throw new IllegalArgumentException("Provided key is Null");
		int index = rank(key);
		if (index == size) return null;
		return keys[index];
	}
	
	public K floor(K key) {
		if (key == null) throw new IllegalArgumentException("Provided key is Null");
		int index = rank(key);
		if (index < size && keys[index].compareTo(key) == 0) return keys[index];
		if (index == 0) return null;
		return keys[index - 1];
	}
	
	public int size(K low, K high) {
		if (low == null || high == null) throw new IllegalArgumentException("Invalid arguments");
		if (low.compareTo(high) > 0) return 0;
		if (contains(high)) return rank(high) - rank(low) + 1;
		return rank(high) - rank(low);
	}
	
	public Iterable<K> keys(K low, K high) {
		if (low == null || high == null) throw new IllegalArgumentException("Invalid arguments");
		Queue<K> queue = new LinkedList<>();
		if (low.compareTo(high) > 0) return queue;
		for (int i = rank(low); i < rank(high); i++) {
			queue.add(keys[i]);
		}
		if(contains(high)) queue.add(high);
		return queue;
	}
	/** Validate Invariant */
	
	public boolean check() {
		return isSorted() && rankCheck();
	}
	
	private boolean isSorted() {
		for (int i = 0; i < size - 1; i++) {
			if (keys[i].compareTo(keys[i + 1]) > 0) return false;
		}
		return true;
	} 
	
	private boolean rankCheck() {
		for (int i = 0; i < size; i++) {
			if(i != rank(keys[i])) {
				return false;
			}
		}
		for (int i = 0; i < size; i++) {
			if(keys[i].compareTo(keys[rank(keys[i])]) != 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
	/*	BinarySearchST<String, Integer> st = new BinarySearchST<>();
		st.put("A", 1);
		st.put("B", 2);
		st.put("C", 3);
		st.put("B", 4);
		st.put("D", 5);
		st.put("E", 5);
		st.put("BBC", 7);
		
		System.out.println(st.check());
		
		for (String string : st.keys()) {
			System.out.println(string);
		}
		
		System.out.println(st.get("B"));
		
		st.delete("B");
		st.delete("A");
		
		System.out.println(st.check());

		for (String string : st.keys()) {
			System.out.println(string);
		} */
		
		 String test = "S E A R C H E X A M P L E"; 
	        String[] keys = test.split("\\s+");
	        int n = keys.length;

	        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
	        for (int i = 0; i < n; i++) 
	            st.put(keys[i], i); 

	        StdOut.println("size = " + st.size());
	        StdOut.println("min  = " + st.min());
	        StdOut.println("max  = " + st.max());
	        StdOut.println();


	        // print keys in order using keys()
	        StdOut.println("Testing keys()");
	        StdOut.println("--------------------------------");
	        for (String s : st.keys()) 
	            StdOut.println(s + " " + st.get(s)); 
	        StdOut.println();


	        // print keys in order using select
	        StdOut.println("Testing select");
	        StdOut.println("--------------------------------");
	        for (int i = 0; i < st.size(); i++)
	            StdOut.println(i + " " + st.select(i)); 
	        StdOut.println();

	        // test rank, floor, ceiling
	        StdOut.println("key rank floor ceil");
	        StdOut.println("-------------------");
	        for (char i = 'A'; i <= 'Z'; i++) {
	            String s = i + "";
	            StdOut.printf("%2s %4d %4s %4s\n", s, st.rank(s), st.floor(s), st.ceiling(s));
	        }
	        StdOut.println();

	        // test range search and range count
	        String[] from = { "A", "Z", "X", "0", "B", "C" };
	        String[] to   = { "Z", "A", "X", "Z", "G", "L" };
	        StdOut.println("range search");
	        StdOut.println("-------------------");
	        for (int i = 0; i < from.length; i++) {
	            StdOut.printf("%s-%s (%2d) : ", from[i], to[i], st.size(from[i], to[i]));
	            for (String s : st.keys(from[i], to[i]))
	                StdOut.print(s + " ");
	            StdOut.println();
	        }
	        StdOut.println();

	        // delete the smallest keys
	        for (int i = 0; i < st.size() / 2; i++) {
	            st.deleteMin();
	        }
	        StdOut.println("After deleting the smallest " + st.size() / 2 + " keys");
	        StdOut.println("--------------------------------");
	        for (String s : st.keys()) 
	            StdOut.println(s + " " + st.get(s)); 
	        StdOut.println();

	        // delete all the remaining keys
	        while (!st.isEmpty()) {
	            st.delete(st.select(st.size() / 2));
	        }
	        StdOut.println("After deleting the remaining keys");
	        StdOut.println("--------------------------------");
	        for (String s : st.keys()) 
	            StdOut.println(s + " " + st.get(s)); 
	        StdOut.println();

	        StdOut.println("After adding back N keys");
	        StdOut.println("--------------------------------");
	        for (int i = 0; i < n; i++) 
	            st.put(keys[i], i); 
	        for (String s : st.keys()) 
	            StdOut.println(s + " " + st.get(s)); 
	        StdOut.println();

	}
}
