package main.com.igmv.search;


public class DefaultHeuristicFunction implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		throw new IllegalStateException(
				"Should not be depending on the DefaultHeuristicFunction.");
		// return 1;
	}

}