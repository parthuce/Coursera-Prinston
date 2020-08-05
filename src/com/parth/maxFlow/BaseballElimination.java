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

	public BaseballElimination(String filename) {
		if (filename == null) {
            throw new IllegalArgumentException("Filename is null.");
        }

		In in = new In(filename);
		N = in.readInt();
		division = new int[N][N];
		win = new int[N];
		loss = new int[N];
		remaining = new int[N];

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

	public int numberOfTeams() {
		return N;
	}

	public Iterable<String> teams() {
		return nameNumberMap.keySet();
	}

	public int wins(String team) {
		validateTeam(team);
		return win[nameNumberMap.get(team)];
	}

	public int losses(String team) {
		validateTeam(team);
		return loss[nameNumberMap.get(team)];
	}

	public int remaining(String team) {
		validateTeam(team);
		return remaining[nameNumberMap.get(team)];
	}

	public int against(String team1, String team2) {
		validateTeam(team1);
		validateTeam(team2);
		return division[nameNumberMap.get(team1)][nameNumberMap.get(team2)];
	}

	public boolean isEliminated(String team) {
		validateTeam(team);
		if (trivialCase(team) != null)
			return true;

		FordFulkerson maxFlow = getMaxFlow(team);
		if (expectedFlow(team) > maxFlow.value())
			return true;
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

	public Iterable<String> certificateOfElimination(String team) {
		validateTeam(team);
		Iterable<String> result = trivialCase(team);
		if (result != null)
			return result;

		FordFulkerson maxFlow = getMaxFlow(team);
		if (expectedFlow(team) >= maxFlow.value()) {
			LinkedList<String> cut = new LinkedList<>();
			for (int v = 0; v < N; v++) {
				if (v == nameNumberMap.get(team))
					continue;
				if (maxFlow.inCut(v)) {
					cut.add(numberNameMap.get(v));
				}
			}
			return cut;
		}
		return null;
	}

	private FordFulkerson getMaxFlow(String team) {
		int s = N;
		int count = N + 1;
		List<FlowEdge> edges = new LinkedList<>();

		for (int i = 0; i < N; i++) {
			if (i == nameNumberMap.get(team))
				continue;
			for (int j = i + 1; j < N; j++) {
				if (j != nameNumberMap.get(team)) {
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

		int t = count;
		for (int i = 0; i < N; i++) {
			if (i == nameNumberMap.get(team)) {
				continue;
			}
			G.addEdge(new FlowEdge(i, t, wins(team) + remaining(team) - win[i]));
		}
		return new FordFulkerson(G, s, t);
	}

	private int expectedFlow(String team) {
		int flow = 0;
		for (int i = 0; i < N; i++) {
			if (i == nameNumberMap.get(team))
				continue;
			for (int j = i + 1; j < N; j++) {
				if (j != nameNumberMap.get(team)) {
					flow += division[i][j];
				}
			}
		}
		return flow;
	}
	
	private void validateTeam(String team) {
		if (team == null || nameNumberMap.get(team) == null) {
            throw new IllegalArgumentException("team is invalid.");
        }
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
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
