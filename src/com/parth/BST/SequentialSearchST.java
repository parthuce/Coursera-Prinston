package com.parth.BST;

import java.util.HashSet;
import java.util.Set;

/** Symbol Table (unordered) linked list of key-value pairs. */

public class SequentialSearchST <K, V> {
	
	Node<K,V> first;
	int size = 0;
	
	public void put(K key, V value) {
		if (key == null) 
			throw new IllegalArgumentException("Key can not be null");
		if(value == null) {
			delete(key);
			return;
		}
		Node<K,V> node = first;
		while (node != null) {
			if (node.getKey().equals(key)) {
				break;
			}
			node = node.next;
		}
		
		if(node != null) {
			node.setValue(value);
		} else {
			Node<K, V> newNode = new Node<>(key, value);
			if (first != null)
				newNode.next = first;
			first = newNode;
		}
		size++;
	}
	
	public V get(K key) {
		Node<K,V> node = first;
		while (node != null) {
			if (node.getKey().equals(key)) {
				return node.getValue();
			}
			node = node.next;
		}
		return null;
	}
	
	public void delete(K key) {
		Node<K,V> node = first;
		Node<K,V> previous = null;
		while (node != null) {
			if (node.getKey().equals(key)) {
				break;
			}
			previous = node;
			node = node.next;
		}
		if(node != null) {
			if(previous != null) {
				previous.next = node.next;
				node.next = null; // Helping GC
			} else {
				first = node.next;
			}
			size--;
		}
	}
	
	public void recursiveDelete(K key) {
		first = delete(first, key);
	}
	
	private Node<K, V> delete(Node<K, V> node, K key) {
		if(node != null) {
			if(node.getKey().equals(key)) {
				size--;
				return node.next;
			}
			node.next = delete(node.next,key);
		}
		return node;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public int size() {
		return size;
	}
	
	Iterable<K> keys() {
		Set<K> keys = new HashSet<>();
		Node<K,V> node = first;
		while (node != null) {
			keys.add(node.getKey());
			node = node.next;
		}
		return keys;
	}
	

	static class Node<K,V> {
		V value;
		K key;
		Node<K, V> next;
		
		Node(K key, V value){
			this.key = key;
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			if (o == this)
				return true;
			if (this.getClass() != o.getClass())
				return false;
            if (o instanceof Node) {
            	Node<?, ?> e = (Node<?, ?>) o;
    			return getKey().equals(e.getKey()); 
            } 
            return false;
		}
		
		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}
	}
	
	public static void main(String[] args) {
		SequentialSearchST<String, Integer> st = new SequentialSearchST<>();
		st.put("A", 1);
		st.put("B", 2);
		st.put("C", 3);
		st.put("B", 4);
		st.put("D", 5);
		
		System.out.println(st.get("B"));
		
		st.recursiveDelete("B");
		st.delete("A");
		for (String string : st.keys()) {
			System.out.println(string);
		}
	}
}
