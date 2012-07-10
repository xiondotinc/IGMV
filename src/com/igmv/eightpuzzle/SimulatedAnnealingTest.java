package com.igmv.eightpuzzle;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.igmv.search.Problem;
import com.igmv.search.simulatedannealing.SimulatedAnnealingSearch;

public class SimulatedAnnealingTest {
	
	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });
	
	public static void main (String [] args ) {
		
		
		System.out.println("\nEightPuzzleDemo Simulated Annealing  Search -->");
		try {
			Problem problem = new Problem(random1,
					new EightPuzzleSuccessorFunction(),
					new EightPuzzleGoalTest(), new ManhattanHeuristicFunction());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch();
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printActions(List actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = (String) actions.get(i);
			System.out.println(action);
		}
	}
	
	private static void printInstrumentation(Properties properties) {
		Iterator keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}
}
