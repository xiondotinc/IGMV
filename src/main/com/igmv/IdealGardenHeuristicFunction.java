package main.com.igmv;

import main.com.igmv.search.HeuristicFunction;

public class IdealGardenHeuristicFunction implements HeuristicFunction{

	// If a variety is left then value should be significantly higher
	public static int W1 = 600;
	
	
	public static int W2 = 10;
	public static int W3 = 100;
	public static int W4 = 80;
	public static int W5 = 500;
	
	/**
	 * heuristic function is defined as follows
	 * 1. The absolute |deviation| of each of the vegetable in the plan is measured
	 * 2. For vegetable required=YES deviation=0 is invalid state
	 * 3. For any vegetable if actualShare < requiredRow/totalRows then it is invalid state
	 * 4. value = SUM(|deviation| * desirabilityIndex) - |deviation|*differenceInRequiredExp for all vegetables
	 */

	/**
	 * 1. The absolute |deviation| of each of the vegetable in the plan
	 * is measured 
	 * 2. For vegetable required=YES RowLength=0 is invalid state 
	 * 3. For any vegetable if 0,2 < |actualShare - requiredRow/totalRows| then it
	 * is invalid state - in this way the result is more flexible - otherwise it
	 * could be solved with simple linear optimisation 
	 * 4. value = W1*NumberOfVarieties + SUM for all vegetables( W2* actualShare *
	 * desirabilityIndex - W3 * actualShare * differenceInRequiredExp) 
	 * - W1,W2,W3 are weights
	 * 
	 * 1. We should also try to reduce the highest deviation.
	 * 2. Try to give less weightage to number of variety and punish more for vegetables with higher required exp than 
	 * 3. When a vegetable is not included its share should be distributed to other vegetables.
	 */
	@Override
	public double getHeuristicValue(Object state) {
		GardenPlan garden = (GardenPlan) state;
		if (!garden.isValid()) {
			throw new IllegalStateException("Illeagl garden state generated after sometime");
			//return Long.MAX_VALUE;
		}
		double value = 0;
		int numVarietyInGarden = 0;
		for (IGMVVariety var : garden.getVarieties()) {
			if (var.getActualRowLength() > 0) {
				numVarietyInGarden++;
			}
		}

		value -= W1 * numVarietyInGarden;
		double highestDev = 0;
		for (IGMVVegetable veg : garden.getVegetables()) {
			value -= (W2 * veg.getActualVegetableQuantity() * veg
					.getDesirabilityIndex());
			
			value += W4 * veg.getDeviationInShare();
			if (highestDev < veg.getDeviationInShare()) {
				highestDev = veg.getDeviationInShare();
			}
			//value += W4 * veg.getPercentageDeviationInShare();
			boolean nullifyDevPunishemnet = false;
			for (IGMVVariety v : veg.getVarieties()) {
				
				if (v.getActualRowLength() > 0
						&& (v.getRequiredExp() > garden.getUserExp())) {
					value += W3
							* (v.getRequiredExp() - garden.getUserExp()) * v.getVarietyShare() ;
					if(!nullifyDevPunishemnet) {
						value -= W4 * veg.getDeviationInShare();
					}
					nullifyDevPunishemnet = true;
				}
//				else {
//					value -= W4 * (veg.getDesiredShare() - v.getVarietyShare());
//				}
			}
		}

		value += W5 * highestDev;
		return value;
	}

}
