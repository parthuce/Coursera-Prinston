package com.parth.Compression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

	// apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	int R = 256;
    	char[] charSequence = new char[R];
    	for (int i = 0; i < R; i++) {
    		charSequence[i] = (char) i;
    	}
    	
    	while (!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar();
    		for (int i = 0; i < R; i ++) {
    			if (c == charSequence[i]) {
    				BinaryStdOut.write(i, 8);
    				System.arraycopy(charSequence, 0, charSequence, 1, i);
    				charSequence[0] = c;
    				break;
    			}
    		}
    	}
		BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	int R = 256;
    	char[] charSequence = new char[R];
    	for (int i = 0; i < R; i++) {
    		charSequence[i] = (char) i;
    	}
    	
    	while (!BinaryStdIn.isEmpty()) {
    		int c = BinaryStdIn.readChar();
			char ch = charSequence[c];
			BinaryStdOut.write(charSequence[c]);
    		System.arraycopy(charSequence, 0, charSequence, 1, c);
			charSequence[0] = ch;
    	}
		BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
    	if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
