package com.parth.trie;

class TrieST<Value> {
	private Node<Value> root = new Node<Value>();
	private static int R = 256;
	
	TrieST() {}
	
	static class Node<Value> {
		private Value v;
		private Node<Value>[] next = new Node[R];
	}
	
	public void put(String key, Value value) {
		root = put(root, key, value, 0);
	}
	
	private Node<Value> put(Node<Value> node, String key, Value val, int d) {
		
		if (node == null) node = new Node<Value>();
		if (d == key.length()) { node.v = val; return node; }
		char c = key.charAt(d);
		node.next[c] = put(node.next[c], key, val, d + 1);
		return node;
	}
	
	public Value get(String key) {
		Node<Value> node = get(root, key , 0);
		if (node == null) return null;
		return node.v;
	}
	
	private Node<Value> get(Node<Value> node, String key, int d) {
		if (node == null) return null;
		if (d == key.length()) return node;
		char c = key.charAt(d);
		return get(node.next[c], key, d + 1);
	}
	
	public void delete(String key) {
		delete(root, key, 0);
	}
	
	private Node<Value> delete(Node<Value> node, String key, int d) {
		if (node == null) return null;
		if (d == key.length()) {
			node.v = null; 
			if (allNullLinks(node)) return null;
			else return node;
		}
		char c = key.charAt(d);
		node.next[c] = delete(node.next[c], key, d + 1);
		if (node.v == null && allNullLinks(node)) return null;
		return node;
	}
	
	private boolean allNullLinks(Node<Value> node) {
		for (Node<Value> x : node.next) {
			if (x != null) return false;
		}
		return true;
	}
	
	public static void main(String args[]) {
		TrieST<Integer> trie = new TrieST<>();
		trie.put("she", 3);
		trie.put("shel", 5);
		trie.put("abc", 10);
		System.out.println(trie.get("shel"));
		trie.delete("she");
		System.out.println(trie.get("shel"));
	}
}