package com.parth.Compression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

	// apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
    	String input = BinaryStdIn.readString();
    	CircularSuffixArray suffixArray = new CircularSuffixArray(input);
    	
    	StringBuilder sb = new StringBuilder(suffixArray.length());
    	for (int i = 0; i < suffixArray.length(); i++) {
    		if (suffixArray.index(i) == 0) {
    			BinaryStdOut.write(i);
    			sb.append(input.charAt(suffixArray.length() - 1));
    		} else {
    			sb.append(input.charAt(suffixArray.index(i) - 1));
    		}
    	}
    	BinaryStdOut.write(sb.toString());
		BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
    	int first = BinaryStdIn.readInt();
    	String input = BinaryStdIn.readString();
    	
    	int n = input.length();
    	int R = 256;
		int[] count = new int[R + 1];
		int[] next = new int[n];
		char aux[] = new char[n];
    	StringBuilder output = new StringBuilder(n);
		
		for (int i = 0; i < n; i++)
			count[input.charAt(i) + 1]++;

		// compute cumulates
		for (int r = 0; r < R; r++)
			count[r + 1] += count[r];

		// move data
		for (int i = 0; i < n; i++) {
			aux[count[input.charAt(i)]] = input.charAt(i);
			next[count[input.charAt(i)]] = i;
			count[input.charAt(i)]++;
		}
    	
		String sortedInput = new String(aux);
    	
    	for (int i = 0; i < input.length(); i++) {
    		output.append(sortedInput.charAt(first));
    		first = next[first];
    	}
    	
    	BinaryStdOut.write(output.toString());
		BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
    	if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
