package main.com.igmv.search.simulatedannealing;


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import main.com.igmv.search.Metrics;
import main.com.igmv.search.Problem;
import main.com.igmv.search.Search;


public class SearchAgent extends Agent {
	List actionList;

	private Iterator actionIterator;

	private Metrics searchMetrics;

	public SearchAgent(Problem p, Search search) throws Exception {
		actionList = search.search(p);
		actionIterator = actionList.iterator();
		searchMetrics = search.getMetrics();

	}

	@Override
	public String execute(Percept p) {
		if (actionIterator.hasNext()) {
			return (String) actionIterator.next();
		} else {
			return "NoOp";
		}
	}

	public List getActions() {
		return actionList;
	}

	public Properties getInstrumentation() {
		Properties retVal = new Properties();
		Iterator iter = searchMetrics.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = searchMetrics.get(key);
			retVal.setProperty(key, value);
		}
		return retVal;
	}

}