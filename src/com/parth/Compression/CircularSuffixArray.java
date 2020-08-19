package com.parth.Compression;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
	
	private int length;
	private int[] index;

	// circular suffix array of s
    public CircularSuffixArray(String s) {
    	if (s == null) throw new IllegalArgumentException("Provided String is not valid");
    	length = s.length();
    	index = new int[length];
    	CircularSuffix[] suffix = new CircularSuffix[length];
    	for (int i = 0; i < length; i++) {
    		suffix[i] = new CircularSuffix(i, s);
    	}
    	
    	Arrays.sort(suffix);
    	
    	for (int i = 0; i < length; i++) {
    		index[i] = suffix[i].index;
    	}
    }

    // length of s
    public int length() {
		return length;
    	
    }

    // returns index of ith sorted suffix
    public int index(int i) {
    	if (i < 0 || i > length - 1) {
    		throw new IllegalArgumentException("Index is not valid.");
    	}
		return index[i];
    }
    
    private class CircularSuffix implements Comparable<CircularSuffix> {
    	private int index;
    	private String str;
    	
    	CircularSuffix(int index, String str) {
    		this.index = index;
    		this.str = str;
    	}
    	
		@Override
		public int compareTo(CircularSuffix that) {
			if (this == that) return 0;
			for (int i = 0; i < str.length(); i++) {
				if (this.charAt(i) > that.charAt(i)) return +1;
				if (this.charAt(i) < that.charAt(i)) return -1;
			}
			return 0;
		}
		
		private char charAt(int i) {
			int offset = i + index;
			if (offset < str.length()) return str.charAt(offset);
			return str.charAt(Math.abs(str.length() - offset));
		}
    }

    // unit testing (required)
    public static void main(String[] args) {
    	CircularSuffixArray csa = new CircularSuffixArray("BBABAAAABB");
        for (int i = 0; i < csa.length(); ++i) {
            StdOut.print(csa.index(i) + " ");
        }
        StdOut.println();
    }
}
