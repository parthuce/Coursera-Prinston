package com.parth.maxFlow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

	private final int N;
	private Map<String, Integer> nameNumberMap;
	private Map<Integer, String> numberNameMap;
	private final int[] win;
	private final int[] loss;
	private final int[] remaining;
	private int[][] division;

	// create a baseball division from given filename in format specified below
	public BaseballElimination(String filename) {

		In in = new In(filename);
		N = in.readInt();
		division = new int[N][N];
		win = new int[N];
		loss = new int[N];
		remaining  = new int[N];
		
		nameNumberMap = new HashMap<>();
		numberNameMap = new HashMap<>();

		for (int i = 0; i < N; i++) {
			String team = in.readString();
			nameNumberMap.put(team, i);
			numberNameMap.put(i, team);
			win[i] = in.readInt();
			loss[i] = in.readInt();
			remaining[i] = in.readInt();

			for (int j = 0; j < N; j++) {
				division[i][j] = in.readInt();
			}
		}
	}
	
	// number of teams
	public int numberOfTeams() {
		return N;
	}

	// all teams
	public Iterable<String> teams() {
		return nameNumberMap.keySet();
	}

	// number of wins for given team
	public int wins(String team) {
		return win[nameNumberMap.get(team)];
	}

	// number of losses for given team
	public int losses(String team) {
		return loss[nameNumberMap.get(team)];
	}

	// number of remaining games for given team
	public int remaining(String team) {
		return remaining[nameNumberMap.get(team)];
	}

	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		return division[nameNumberMap.get(team1)][nameNumberMap.get(team2)];
	}

	// is given team eliminated?
	public boolean isEliminated(String team) {
		
		if (trivialCase(team) != null) return true;
		
		List<FlowEdge> edges = new LinkedList<>();
		
		int s = N - 1;
		int count  = N;
		int flow = 0;
		for (int i = 0; i < N; i++) {
			if (i == nameNumberMap.get(team)) continue;
			for (int j = i + 1; j < N ; j++) {
				if (j != nameNumberMap.get(team)) {
					flow += division[i][j];
					edges.add(new FlowEdge(s, count, division[i][j]));
					edges.add(new FlowEdge(count, i, Double.POSITIVE_INFINITY));
					edges.add(new FlowEdge(count, j, Double.POSITIVE_INFINITY));
					count++;
				}
			}
		}
		
		FlowNetwork G = new FlowNetwork(count + 1);
		for (FlowEdge e : edges) {
			G.addEdge(e);
		}
		
		int t = count ;
		for (int i = 0, j = 0; i < N; i++) {
			if (i == nameNumberMap.get(team)) {
				continue;
			}
			G.addEdge(new FlowEdge(j, t, wins(team) + remaining(team) - win[i]));
			j++;
		}
		
		
		FordFulkerson maxFlow = new FordFulkerson(G, s, t);
		
		if (flow > maxFlow.value()) return true;

		return false;
		
	}

	private Iterable<String> trivialCase(String team) {
		int maxWin = win[nameNumberMap.get(team)] + remaining[nameNumberMap.get(team)];
		for (String t : teams()) {
			if (maxWin < win[nameNumberMap.get(t)]) {
				return Arrays.asList(t);
			}
		}
		return null;
	}

	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		Iterable<String> result = trivialCase(team);
		if (result != null) return result;
		
		List<FlowEdge> edges = new LinkedList<>();
		
		int s = N - 1;
		int count  = N;
		int flow = 0;
		for (int i = 0; i < N; i++) {
			if (i == nameNumberMap.get(team)) continue;
			for (int j = i + 1; j < N ; j++) {
				if (j != nameNumberMap.get(team)) {
					flow += division[i][j];
					edges.add(new FlowEdge(s, count, division[i][j]));
					edges.add(new FlowEdge(count, i, Double.POSITIVE_INFINITY));
					edges.add(new FlowEdge(count, j, Double.POSITIVE_INFINITY));
					count++;
				}
			}
		}
		
		FlowNetwork G = new FlowNetwork(count + 1);
		for (FlowEdge e : edges) {
			G.addEdge(e);
		}
		
		int t = count ;
		for (int i = 0, j = 0; i < N; i++) {
			if (i == nameNumberMap.get(team)) {
				continue;
			}
			G.addEdge(new FlowEdge(j, t, wins(team) + remaining(team) - win[i]));
			j++;
		}
		
		
		FordFulkerson maxFlow = new FordFulkerson(G, s, t);
		
		if (flow > maxFlow.value()) {
			LinkedList<String> cut = new LinkedList<>();
			for (int v = 0; v < N ; v++) {
				if (v == nameNumberMap.get(team)) continue;
				if (maxFlow.inCut(v)) {
					cut.add(numberNameMap.get(v));
				}
			}
			return cut;
		}
		return null;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination("/Users/parth/Downloads/baseball/teams4.txt");
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}

}
