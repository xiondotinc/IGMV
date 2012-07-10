package com.igmv;

import com.igmv.search.GoalTest;

public class GardenPlanGoalTest implements GoalTest {

	private GardenType idealType;

	public GardenPlanGoalTest(GardenType type) {
		this.idealType = type;
	}

	@Override
	public boolean isGoalState(Object state) {
		GardenPlan plan = (GardenPlan) state;

		for (Vegetable veg : idealType.getVegetables()) {
			double desiredShare = veg.getDesiredShare();
			double vegShare = 0;
			for (Variety v : plan.getVarieties()) {
				if (veg.getVarieties().contains(v)) {
					vegShare += v.getVarietyShare();
				}
			}
			if (desiredShare != vegShare) {
				return false;
			}
		}
		return true;
	}

}
