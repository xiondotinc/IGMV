package main.com.igmv;

import main.com.igmv.search.GoalTest;

public class GardenPlanGoalTest implements GoalTest {

	private GardenType idealType;

	private int userExp;

	public GardenPlanGoalTest(GardenType type, int exp) {
		this.idealType = type;
		this.userExp = exp;
	}

	/**
	 * Goal state is a state where all the vegetables of the plan is in same
	 * proportion in the given state. 
	 * It should also ensure that that maximum variety of vegetables are 
	 * in the state.
	 * 
	 */
	@Override
	public boolean isGoalState(Object state) {
		// there is no goal state
		return false;
//		GardenPlan plan = (GardenPlan) state;
//
//		for (Variety v : plan.getVarieties()) {
//			if (v.getRequiredExp() <= userExp) {
//
//				if (!plan.getVarieties().contains(v)) {
//					return false;
//				}
//			}
//		}
//		
//		for (Vegetable veg : idealType.getVegetables()) {
//			double desiredShare = veg.getDesiredShare();
//			double vegShare = 0;
//			
//			for (Variety v : plan.getVarieties()) {
//				if (veg.getVarieties().contains(v)) {
//					vegShare += v.getVarietyShare();
//				}
//			}
//			if (Math.abs(desiredShare - vegShare) > IGMV.tolerance) {
//				return false;
//			}
//		}
//		return true;
	}

}
