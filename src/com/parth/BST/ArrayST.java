package com.parth.BST;

import java.util.LinkedList;
import java.util.Queue;

public class ArrayST<K,V> {

	K[] keys;
	V[] values;
	int size = 0;
	
	ArrayST() {
		keys = (K[]) new Object[1];
		values = (V[]) new Object[1];
	}
	
	public void delete(K key) {
		for (int i = 0; i < size; i++) {
			if (keys[i].equals(key)) {
				keys[i] = keys[size - 1];
				values[i] = values[size - 1];
				keys[size - 1] = null; //To avoid loitering.
				values[size - 1] = null; //To avoid loitering.
				size--;
				if(size > 0 && size == keys.length/4) resize(keys.length/2);
				return;
			}
		}
		
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void put(K key, V value) {
		for (int i = 0; i < size; i++) {
			if (keys[i].equals(key)) {
				values[i] = value;
				return;
			}
		}
		
		keys[size] = key;
		values[size] = value;
		size++;
		
		if (size == values.length) resize(size * 2);
	}
	
	private void resize(int newSize) {
		K[] newKeys = (K[]) new Object[newSize];
		V[] newValues = (V[]) new Object[newSize];
		
		for (int i = 0; i < size; i++) {
			newKeys[i] = keys[i];
			newValues[i] = values[i];
		}
		keys = newKeys;
		values = newValues;
	}

	public V get(K key) {
		for (int i = 0; i < size; i++) {
			if (keys[i].equals(key)) {
				return values[i];
			}
		}
		return null;
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public Iterable<K> keys() {
		Queue<K> queue = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			queue.add(keys[i]);
		}
		return queue;
	}
	
	public static void main(String[] args) {
		ArrayST<String, Integer> st = new ArrayST<>();
		st.put("A", 1);
		st.put("B", 2);
		st.put("C", 3);
		st.put("B", 4);
		st.put("D", 5);
		st.put("E", 5);
		
		System.out.println(st.get("B"));
		
		st.delete("B");
		st.delete("A");
		for (String string : st.keys()) {
			System.out.println(string);
		}
	}
}
