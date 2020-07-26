package com.parth.Graph;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Test {
	
	public static void main(String[] args) {
		// Creating an empty Stack 
        Stack<String> stack = new Stack<String>(); 
  
        // Use add() method to add elements into the Stack 
        stack.add("Welcome"); 
        stack.add("To"); 
        stack.add("Geeks"); 
        stack.add("4"); 
        stack.add("Geeks"); 
  
        // Displaying the Stack 
        System.out.println("Stack: " + stack); 
  
        // Creating an iterator 
        Iterator value = stack.iterator(); 
  
        // Displaying the values 
        // after iterating through the stack 
        System.out.println("The iterator values are: "); 
        while (value.hasNext()) { 
            System.out.println(value.next()); 
        } 
        
        Deque<Integer> deque = new LinkedList<>();
        Queue<Integer> queue;
        
        deque.add(1);
        deque.push(1);
        

	}
	

}
