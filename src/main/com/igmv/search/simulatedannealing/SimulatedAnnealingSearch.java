package main.com.igmv.search.simulatedannealing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.com.igmv.search.Node;
import main.com.igmv.search.NodeExpander;
import main.com.igmv.search.Problem;
import main.com.igmv.search.Scheduler;
import main.com.igmv.search.Search;
import main.com.igmv.search.SearchUtils;
import main.com.igmv.search.Util;


/**
 * Simulated Annealing algorithm.
 * <code>
 * function SIMULATED-ANNEALING(problem, schedule) returns a solution state
 *   inputs: problem, a problem
 *           schedule, a mapping from time to "temperature"
 *   local variables: current, a node
 *                    next, a node
 *                    T, a "temperature" controlling the probability of downward steps
 *                    
 *   current <- MAKE-NODE(INITIAL-STATE[problem])
 *   for t <- 1 to INFINITY do
 *     T <- schedule[t]
 *     if T = 0 then return current
 *     next <- a randomly selected successor of current
 *     /\E <- VALUE[next] - VALUE[current]
 *     if /\E > 0 then current <- next
 *     else current <- next only with probablity e^(/\E/T)
 * </code>
 * The simulated annealing search algorithm, a version of the
 * stochastic hill climbing where some downhill moves are allowed. Downhill
 * moves are accepted readily early in the annealing schedule and then less
 * often as time goes on. The schedule input determines the value of T as a
 * function of time.
 */

/**
 * @author Suranjan Kumar
 * 
 */
public class SimulatedAnnealingSearch extends NodeExpander implements Search {

	public enum SearchOutcome {
		FAILURE, SOLUTION_FOUND
	};

	private final Scheduler scheduler;

	private SearchOutcome outcome = SearchOutcome.FAILURE;

	private Object lastState = null;

	public SimulatedAnnealingSearch() {
		this.scheduler = new Scheduler();
	}

	// function SIMULATED-ANNEALING(problem, schedule) returns a solution state
	// inputs: problem, a problem
	// schedule, a mapping from time to "temperature"
	public List<String> search(Problem p) throws Exception {
		// local variables: current, a node
		// next, a node
		// T, a "temperature" controlling the probability of downward steps
		clearInstrumentation();
		outcome = SearchOutcome.FAILURE;
		lastState = null;
		// current <- MAKE-NODE(INITIAL-STATE[problem])
		Node current = new Node(p.getInitialState());
		Node next = null;
		List<String> ret = new ArrayList<String>();
		// for t <- 1 to INFINITY do
		int timeStep = 0;
		while (true) {
			// temperature <- schedule[t]
			double temperature = scheduler.getTemp(timeStep);
			timeStep++;
			// if temperature = 0 then return current
			if (temperature == 0.0) {
				if (p.isGoalState(current.getState())) {
					outcome = SearchOutcome.SOLUTION_FOUND;
				}
				ret = SearchUtils.actionsFromNodes(current.getPathFromRoot());
				lastState = current.getState();
				break;
			}

			List<Node> children = expandNode(current, p);
			if (children.size() > 0) {
				// next <- a randomly selected successor of current
				next = Util.selectRandomlyFromList(children);
				// /\E <- VALUE[next] - VALUE[current]
				double deltaE = getValue(p, next) - getValue(p, current);

				if (shouldAccept(temperature, deltaE)) {
					current = next;
				}
			}
		}

		return ret;
	}

	// if /\E > 0 then current <- next
	// else current <- next only with probability e^(/\E/T)
	private boolean shouldAccept(double temperature, double deltaE) {
		return //(deltaE > 0.0) ||
				 (new Random().nextDouble() <= probabilityOfAcceptance(
						temperature, deltaE));
	}

	public double probabilityOfAcceptance(double temperature, double deltaE) {
		return Math.exp(deltaE / temperature);
	}

	public SearchOutcome getOutcome() {
		return outcome;
	}

	public Object getLastSearchState() {
		return lastState;
	}
	/**
	 * assumption greater heuristic value =>
	 * HIGHER on hill; 0 == goal state;
	 * SA deals with gradient DESCENT
	 * @param p
	 * @param n
	 * @return
	 */
	private double getValue(Problem p, Node n) {
		return -1 * getHeuristic(p, n); 
	}

	private double getHeuristic(Problem p, Node aNode) {
		return p.getHeuristicFunction().getHeuristicValue(aNode.getState());
	}
}