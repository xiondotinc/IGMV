package main.com.igmv;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import main.com.igmv.eightpuzzle.SearchAgent;
import main.com.igmv.input.ExcelReader;
import main.com.igmv.search.Problem;
import main.com.igmv.search.Successor;
import main.com.igmv.search.simulatedannealing.SimulatedAnnealingSearch;


public class IGMV {
	private final static Logger LOGGER = Logger.getLogger(IGMV.class.getName()); 
	private static String dataFile = "res/data.xls";
	private static String gardenTypeFile = "res/garden type.xls";
	
	public static int userExp = 1;
	public static double tolerance = 0.00001;
	
	public static void main(String[] args) throws IOException {
		GardenSize size = new GardenSize(10,10);
		
		// Read the type of the garden user wants.
		GardenType type = ExcelReader.readGardenType(gardenTypeFile, size);
		// Read the data from the data file. It contains information
		// about the variety.
		Set<Variety> varieties = ExcelReader.readData(dataFile, size);
		
		// Populate all the vegetables in the type with the data.
		// the data will be what all varieties are available
		// and for each of the variety minimum row length,
		// required experience etc.
		for (Vegetable veg : type.getVegetables()) {
			for (Variety v : varieties) {
				if (v.getVegetableName().equals(veg.getName())) {
					veg.addVariety(v);
				}
			}
		}
		//System.out.println("The Garden type is : \n" + type + " total veg " + type.getVegetables().size());

		// Create the first garden by populating varieties with more than minimum
		// row length required
		// It should have all the vegetables from the plan which have required=true

		GardenPlan initialPlan = new GardenPlan(userExp, size, type.getVegetables());
		
		System.out.println(" The intital plan is \n"
				+ initialPlan
				+ " the heuristic value is "
				+ new IdealGardenHeuristicFunction()
						.getHeuristicValue(initialPlan));
		
//		GardenPlanGoalTest goalTest = new GardenPlanGoalTest(type, userExp);
		//System.out.println(goalTest.isGoalState(initialPlan));
		
		GardenPlanSuccessorFunction f = new GardenPlanSuccessorFunction();
		List<Successor> su = f.getSuccessors(initialPlan);
		
		System.out.println("Successor is "
				+ ((Successor) su.get(0)).getState()
				+ " the heuristic value is "
				+ new IdealGardenHeuristicFunction()
						.getHeuristicValue(((Successor) su.get(0)).getState()));
		
		try {
			Problem problem = new Problem(initialPlan,
					new GardenPlanSuccessorFunction(),
					new GardenPlanGoalTest(type,userExp), new IdealGardenHeuristicFunction());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch();
			SearchAgent agent = new SearchAgent(problem, search);
			
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
			
			System.out.println("Last state is "
			+ " the heuristic value is "
			+ new IdealGardenHeuristicFunction()
					.getHeuristicValue(search.getLastSearchState()));
			
			System.out.println("It is valid : " + ((GardenPlan)(search.getLastSearchState())).isValid());
			
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
