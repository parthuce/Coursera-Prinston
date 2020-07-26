package com.parth.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

	private final Map<String, List<Integer>> synsetsMap;
	private final Map<Integer, String> id2Noun;
	private final SAP sap;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null)
			throw new IllegalArgumentException("synsets file name must be valid");
		if (hypernyms == null)
			throw new IllegalArgumentException("hypernyms file name must be valid");

		synsetsMap = new HashMap<>();
		id2Noun = new HashMap<>();
		synset(synsets);
		sap = new SAP(hypernym(hypernyms));
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return synsetsMap.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null)
			throw new IllegalArgumentException("word must be valid");
		return synsetsMap.containsKey(word);
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new IllegalArgumentException("Arguments must be valid");
		return sap.length(synsetsMap.get(nounA), synsetsMap.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA
	// and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new IllegalArgumentException("Arguments must be valid");
		return id2Noun.get(sap.ancestor(synsetsMap.get(nounA), synsetsMap.get(nounB)));

	}

	/** Private Helper Methods 
	 * @return */
	private Digraph hypernym(String hypernyms) {
		Digraph G = new Digraph(id2Noun.size());

		// Prepare Graph by adding edge
		In in = new In(hypernyms);
		while (in.hasNextLine()) {
			String line = in.readLine();
			String[] elemets = line.split(",");
			for (int i = 1; i < elemets.length; i++) {
				G.addEdge(Integer.parseInt(elemets[0]), Integer.parseInt(elemets[i]));
			}
		}

		// Cycle detection
		DirectedCycle dc = new DirectedCycle(G);
		if (dc.hasCycle()) {
			throw new IllegalArgumentException("Cycle detected");
		}

		// More than one root detection
		int numRoot = 0;
		for (int i = 0; i < G.V(); ++i) {
			if (G.outdegree(i) == 0) {
				++numRoot;
				if (numRoot > 1) {
					throw new IllegalArgumentException("More than 1 root");
				}
			}
		}
		return G;
	}

	private void synset(String synsets) {
		In in = new In(synsets);
		while (in.hasNextLine()) {
			String[] elements = in.readLine().split(",");
			id2Noun.put(Integer.parseInt(elements[0]), elements[1]);
			for (String noun : elements[1].split(" ")) {
				if (synsetsMap.containsKey(noun)) {
					synsetsMap.get(noun).add(Integer.parseInt(elements[0]));
				} else {
					List<Integer> listOfIds = new ArrayList<Integer>();
					listOfIds.add(Integer.parseInt(elements[0]));
					synsetsMap.put(noun, listOfIds);
				}
			}
		}
	}

	// do unit testing of this class
	public static void main(String[] args) {

	}

}
