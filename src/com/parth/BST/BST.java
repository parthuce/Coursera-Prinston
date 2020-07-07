package com.parth.BST;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.princeton.cs.algs4.StdOut;

public class BST<K extends Comparable<K>, V> {
	
	Node<K,V> root;

	static class Node<K, V> {
		K key;
		V value;
		Node<K, V> left, right;
		int size = 0;

		Node(K key, V value, Node<K, V> left, Node<K, V> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}
		
		Node(K key, V value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}

		K getKey() {
			return key;
		}

		V getValue() {
			return value;
		}
	}
	// My first code
	public void putI(K key, V value) {
		if (key == null) throw new IllegalArgumentException("Key can't be null");
		if (value == null) return;
	
		Node<K,V> node = root, prev = null;
		while(node != null) {
			if(node.getKey().compareTo(key) == 0) {
				node.value = value;
				return;
			} 
			
			prev = node;
			
			if (node.getKey().compareTo(key) > 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		
		node = new Node<K,V>(key, value, 1);
		if(prev == null) {
			root = node;
			return;
		}
		
		if(prev.getKey().compareTo(key) > 0) {
			prev.left = node;
		} else {
			prev.right = node;
		}
	}
	
	public void put(K key, V value) {
		if (key == null) throw new IllegalArgumentException("Key can't be null");
		if (value == null) return;
		
		if(root == null) {
			root = new Node<K,V>(key, value, 1);
			return;
		}
		put(root, key, value);
	}
	
	private void put(Node<K,V> node, K key, V value) {
		int comp = node.key.compareTo(key);
		if(comp == 0) {
			node.value = value;
		} else if (comp > 0) {
			if(node.left == null) {
				node.left = new Node<K,V>(key, value, 1);
			} else {
				put(node.left, key, value);
			}
		} else {
			if(node.right == null) {
				node.right = new Node<K,V>(key, value, 1);
			} else {
				put(node.right, key, value);
			}
		}
		node.size = size(node.left) + 1 + size(node.right);
	}
	
	public V get(K key) {
		if (key == null) throw new IllegalArgumentException("Key can't be null");
		Node<K,V> node = root;
		while(node != null) {
			if(node.getKey().compareTo(key) == 0) {
				return node.getValue();
			} 
			if (node.getKey().compareTo(key) > 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return null;
	}
	
	public void delete(K key) {
		if (key == null) throw new IllegalArgumentException("Key can't be null");
		root = delete(root, key);
	}
	
	private Node<K, V> delete(Node<K, V> node, K key) {
		if (node == null) return null;
		int comp = key.compareTo(node.key);
		if (comp < 0) {
			node.left = delete(node.left, key);
		}
		else if (comp > 0) {
			node.right = delete(node.right, key);
		}
		else {
			if (node.left == null) return node.right;
			if (node.right == null) return node.left;
			
			Node<K,V> tempCurr = node;
			node = findMin(node.right);
			node.right = deleteMin(tempCurr.right);
			node.left = tempCurr.left;
			
		}
		node.size = size(node.left) + 1 + size(node.right);
		return node;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(Node<K,V> node) {
		if (node == null) return 0;
		return node.size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	Iterable<K> keys() {
		Queue<K> queue = new LinkedList<>();
		inorderTraversal(root, queue);
		return queue;
	}
	
	private void inorderTraversal(Node<K, V> node, Queue<K> queue) {
		if(node == null) return;
		inorderTraversal(node.left, queue);
		queue.add(node.key);
		inorderTraversal(node.right, queue);
	}
	
	/** Ordered symbol table methods */
	public K min() {
		if (isEmpty()) throw new NoSuchElementException("Called min() with empty symbol table");
		return findMin(root).key;
	}

	private Node<K, V> findMin(Node<K, V> node) {
		if (node.left == null) return node;
		return findMin(node.left);
	}
	
	public K max() {
		if (isEmpty()) throw new NoSuchElementException("Called min() with empty symbol table");
		return findMax(root).key;
	}

	private Node<K,V> findMax(Node<K, V> node) {
		if(node.right == null) return node;
		return findMax(node.right);
	}
	
	//Good to know method.
	public K select(int k) {
		if (k < 0 || k >= size()) throw new IllegalArgumentException("Called select with invalid argument: " + k);
		return select(root, k);
	}

	private K select(Node<K, V> node, int k) {
		if (node == null) return null;
		if (size(node.left) > k) return select(node.left, k);
		else if (size(node.left) < k) return select(node.right, k - size(node.left) - 1);
		return node.key;
	}
	
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Called min() with empty symbol table");
		root = deleteMin(root);
	}
	// Master piece
	private Node<K,V> deleteMin(Node<K, V> node) {
		if(node.left == null) return node.right;
		node.left = deleteMin(node.left);
		node.size = size(node.left) + 1 + size(node.right);
		return node;
	}
	
	public void deleteMax() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
		root = deleteMax(root);
	}

	private Node<K,V> deleteMax(Node<K, V> node) {
		if(node.right == null) return node.left;
		node.right = deleteMax(node.right);
		node.size = size(node.left) + 1 + size(node.right);
		return node;
	}
	// Same as Binary Array ST (BinarySearchST)
	public int size(K low, K high) {
		if (low == null || high == null) throw new IllegalArgumentException("Invalid arguments");
		if (low.compareTo(high) > 0) return 0;
		if (contains(high)) return rank(high) - rank(low) + 1;
		else return rank(high) - rank(low);
	}

	//My Implementation
	public int rank(K key) {
		if (key == null) throw new IllegalArgumentException("Key Can't be null");
		return rank(root, key, 0);
	}
	
	private int rank(Node<K,V> node, K key, int rank) {
		if (node == null) return 0;
		int comp = key.compareTo(node.key);
		if (comp < 0) return rank(node.left, key, rank);
		else if (comp > 0) return rank(node.right, key, rank + size(node.left) + 1);
		else return rank + 1;
	}
	
	//Princeton BST implementation
	public int rankBST(K key) {
		if (key == null) throw new IllegalArgumentException("Key Can't be null");
		return rankBST(root, key);
	}
	
	private int rankBST(Node<K,V> node, K key) {
		if (node == null) return 0;
		int comp = key.compareTo(node.key);
		if (comp < 0) return rankBST(node.left, key);
		else if (comp > 0) return 1 + size(node.left) + rankBST(node.right, key);
		else return size(node.left);
	}

	
	
	public Iterable<K> keys(K low, K high) {
		if (low == null || high == null) throw new IllegalArgumentException("Invalid arguments");
		Queue<K> queue = new LinkedList<>();
		inorderTraversal(root, queue, low, high);
		return queue;
		
	}
	// Good thoughts
	private void inorderTraversal(Node<K, V> node, Queue<K> queue, K low, K high) {
		if (node == null) return;
		int lowComp = low.compareTo(node.key);
		int highComp = high.compareTo(node.key);
		if (lowComp < 0) inorderTraversal(node.left, queue, low, high);
		if (highComp > 0) inorderTraversal(node.right, queue, low, high);
		if (lowComp >= 0 && highComp <= 0) queue.add(node.key);
	}
	
	public K floor(K key) {
		if (key == null) throw new IllegalArgumentException("Provided key is Null");
		Node<K,V> node = floor(root, key);
		if(node == null) throw new NoSuchElementException("Argument is too small!");
		return node.key;
	}

	private Node<K,V> floor(Node<K, V> node, K key) {
		if (node == null) return null;
		int comp = key.compareTo(node.key);
		if (comp == 0) return node;
		if (comp < 0) return floor(node.left, key);
		Node<K, V> floor = floor(node.right, key);
		if (floor != null) return floor;
		return node;
	}
	
	public K floor2(K key) {
        K x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private K floor2(Node<K,V> x, K key, K best) {
        if (x == null) return best;
        int cmp = key.compareTo(x.key);
        if      (cmp  < 0) return floor2(x.left, key, best);
        else if (cmp  > 0) return floor2(x.right, key, x.key);
        else               return x.key;
    } 
	
	public K ceiling(K key) {
		if (key == null) throw new IllegalArgumentException("Provided key is Null");
		Node<K,V> node = ceiling(root, key);
		if(node == null) throw new NoSuchElementException("Argument is too high!");
		return node.key;
	}

	private Node<K, V> ceiling(Node<K, V> node, K key) {
		if (node == null) return null;
		int comp = key.compareTo(node.key);
		if (comp == 0) return node;
		if (comp > 0) return ceiling(node.right, key);
		if (comp < 0) {
			Node<K, V> ceiling = ceiling(node.left, key);
			if(ceiling != null) return ceiling; 
		}
		return node;
	}

	public static void main(String[] args) {
		/* BST<String, Integer> st = new BST<>();
		st.putR("A", 1);
		st.putR("B", 2);
		st.putR("C", 3);
		st.putR("B", 4);
		st.putR("D", 5);
		st.putR("E", 8);
		st.putR("AAA", 14);

		
		System.out.println(st.get("B"));
		for (String string : st.keys()) {
			System.out.println(string);
		}
		
		System.out.println(st.min()); */
		
		String test = "S E A R C H E X A M P L E"; 
        String[] keys = test.split(" "); 
        int n = keys.length; 
        BST<String, Integer> st = new BST<String, Integer>();
        for (int i = 0; i < n; i++) 
            st.put(keys[i], i); 

        StdOut.println("size = " + st.size());
        StdOut.println("min  = " + st.min());
        StdOut.println("max  = " + st.max());
        StdOut.println();


        // print keys in order using allKeys()
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
        StdOut.println("key rank floor flor2 ceil");
        StdOut.println("-------------------------");
        for (char i = 'A'; i <= 'X'; i++) {
            String s = i + "";
            StdOut.printf("%2s %4d %4s %4s %4s\n", s, st.rank(s), st.floor(s), st.floor2(s), st.ceiling(s));
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

        StdOut.println("After adding back the keys");
        StdOut.println("--------------------------------");
        for (int i = 0; i < n; i++) 
            st.put(keys[i], i); 
        for (String s : st.keys()) 
            StdOut.println(s + " " + st.get(s)); 
        StdOut.println();
	}
}
