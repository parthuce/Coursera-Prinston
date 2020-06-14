package com.parth.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
	
	private Node head, tail;
	private int size = 0;
 
	
	private class Node {
		Item item;
		Node next;
		Node prev;
		
		Node(Item item) {
			this.item = item;
		}
	}
	
	// construct an empty deque
    public Deque() {
    	
    }

    // is the deque empty?
    public boolean isEmpty() {
		return size == 0;
    }

    // return the number of items on the deque
    public int size() {
		return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	validate(item);
    	
    	Node node = new Node(item);
    	
    	if(isEmpty()) {
    		head = tail = node;
    	} else {
    		head.prev = node;
    		node.next = head;
    		head = node;
    	}
    	size++;
    }

    private void validate(Item item) {
    	if(item == null) throw new IllegalArgumentException("Item connot be null."); 
	}

	// add the item to the back
    public void addLast(Item item) {
    	validate(item);
    	
    	Node node = new Node(item);

    	if(isEmpty()) {
    		head = tail = node;
    	} else {
    		tail.next = node;
    		node.prev = tail;
    		tail = node;
    	}
    	size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if(isEmpty()) throw new NoSuchElementException("Deque is empty.");
    	
    	Node node = head;
    	head = head.next;
    	node.next = null;
    	if(head != null) 
    		head.prev = null;
    	
    	size--;
		return node.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if(isEmpty()) throw new NoSuchElementException("Deque is empty.");
    
    	Node node = tail;
    	tail = tail.prev;
    	node.prev = null;
    	if(tail != null) 
    		tail.next = null;
    	
    	size--;
		return node.item;
    }

    // return an iterator over items in order from front to back

    public Iterator<Item> iterator() {
		return new DequeIterator();
	}
    
    private class DequeIterator implements Iterator<Item> {
    	
        private Node curr = head;

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            Item item = curr.item;
            curr = curr.next;
			return item;
		}
    }
    
    /**
     * Serialization of the queue.
     *
     * TODO remove this method before your submission.
     */
    @Override
    public String toString() {
        String result = "";
        for (Item item : this) {
            result += "," + item;
        }
        if (!result.isEmpty()) {
            result = result.substring(1);
        }
        return "[" + result + "]";
    }

    // unit testing (required)
    public static void main(String[] args) {
    	StdOut.println("Tests start.");

        // Test 1: public operations
        Deque<Integer> d1 = new Deque<>();
        StdOut.println("Test 1A passed? " + d1.isEmpty());
        StdOut.println("Test 1B passed? " + d1.toString().equals("[]"));
        d1.addLast(1);
        d1.addLast(2);
        StdOut.println("Test 1C passed? " + d1.toString().equals("[1,2]"));
        StdOut.println("Test 1D passed? " + (d1.size() == 2));
        StdOut.println("Test 1E passed? " + (d1.iterator().next() == 1));
        d1.addFirst(0);
        StdOut.println("Test 1F passed? " + d1.toString().equals("[0,1,2]"));
        d1.removeLast();
        StdOut.println("Test 1G passed? " + d1.toString().equals("[0,1]"));

        d1.removeFirst();
        StdOut.println("Test 1H passed? " + d1.toString().equals("[1]"));
        d1.removeFirst();
        StdOut.println("Test 1I passed? " + d1.toString().equals("[]"));
        StdOut.println("Test 1J passed? " + d1.isEmpty());
        StdOut.println("Test 1H passed? " + !d1.iterator().hasNext());

        // Test 2: exceptions
        Deque<Integer> d2 = new Deque<>();
        try {
            d2.removeFirst();
            StdOut.println("Test 2A passed? " + false);
        } catch (Exception e) {
            boolean result = e instanceof NoSuchElementException;
            StdOut.println("Test 2A passed? " + result);
        }
        try {
            d2.removeLast();
            StdOut.println("Test 2B passed? " + false);
        } catch (Exception e) {
            boolean result = e instanceof NoSuchElementException;
            StdOut.println("Test 2B passed? " + result);
        }
        try {
            d2.addFirst(null);
            StdOut.println("Test 2C passed? " + false);
        } catch (Exception e) {
            boolean result = e instanceof IllegalArgumentException;
            StdOut.println("Test 2C passed? " + result); 
        }
        try {
            d2.addLast(null);
            StdOut.println("Test 2D passed? " + false); 
        } catch (Exception e) {
            boolean result = e instanceof IllegalArgumentException;
            StdOut.println("Test 2D passed? " + result);
        }
        try {
            d2.iterator().remove();
            StdOut.println("Test 2F passed? " + false);
        } catch (Exception e) {
            boolean result = e instanceof UnsupportedOperationException;
            StdOut.println("Test 2F passed? " + result);
        }
        try {
            d2.iterator().next();
            StdOut.println("Test 2G passed? " + false);
        } catch (Exception e) {
            boolean result = e instanceof NoSuchElementException;
            StdOut.println("Test 2G passed? " + result);
        }

        // Test 3: types
        Deque<String> d3a = new Deque<>();
        d3a.addFirst("Hello Algorithm");
        StdOut.println("Test 3A passed? " + true);
        Deque<Double> d3b = new Deque<>();
        d3b.addLast(3.1415926);
        StdOut.println("Test 3B passed? " + true);

        StdOut.println("Tests finished.");
    }
}
