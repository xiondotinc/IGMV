package com.igmv;

import com.igmv.search.HeuristicFunction;

public class IdealGardenHeuristicFunction implements HeuristicFunction{

	/**
	 * heuristic function is defined as follows
	 * 1. The absolute |deviation| of each of the vegetable in the plan is measured
	 * 2. For vegetable required=YES deviation=0 is invalid state
	 * 3. For any vegetable if actualShare < requiredRow/totalRows then it is invalid state
	 * 4. value = SUM(|deviation| * desirabilityIndex) - |deviation|*differenceInRequiredExp for all vegetables
	 */
	@Override
	public double getHeuristicValue(Object state) {
		GardenPlan garden = (GardenPlan) state;
		
		
		
		return 0;
	}

}
