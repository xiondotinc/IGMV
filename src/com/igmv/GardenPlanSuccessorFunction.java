package com.igmv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.igmv.search.Successor;
import com.igmv.search.SuccessorFunction;

public class GardenPlanSuccessorFunction implements SuccessorFunction {

	@Override
	public List getSuccessors(Object state) {
		GardenPlan plan = (GardenPlan) state;
		List<Vegetable> l = new ArrayList<Vegetable>();
		l.addAll(plan.getVegetables());
		Collections.sort(l);
		System.out.println(" The sorted list of vegetable is : " + l);
		String action = swapVegetables(l, plan.getMinRowlengthToSwap());
		Set<Vegetable> successorVegSet = new HashSet<Vegetable>();
		successorVegSet.addAll(l);

		GardenPlan newPlan = new GardenPlan(plan.getUserExp(), plan.getSize()
				.getLength(), plan.getSize().getWidth(), successorVegSet);

		List<Successor> successors = new ArrayList<Successor>();
		successors.add(new Successor(action, newPlan));

		return successors;
	}

	private String  swapVegetables(List<Vegetable> vegList, double minRowLength) {
		boolean swapped = false;
		Vegetable swapFrom = null, swapTo = null;
		while (!swapped) {
			double totalPositiveDeviation = 0, totalNegativeDeviation = 0;
			for (Vegetable v : vegList) {
				if (v.getActualDeviationInShare() > 0) {
					totalPositiveDeviation += v.getActualDeviationInShare();
				}
				if (v.getActualDeviationInShare() < 0) {
					totalNegativeDeviation += v.getActualDeviationInShare();
				}
			}

			Random r = new Random();
			double random = r.nextDouble() * totalPositiveDeviation;
			totalPositiveDeviation = 0;
			
			for (Vegetable v : vegList) {
				if (v.getActualDeviationInShare() > 0) {
					totalPositiveDeviation += v.getActualDeviationInShare();
					if (totalPositiveDeviation > random) {
						swapFrom = v;
						break;
					}
				}
			}

			random = r.nextDouble() * totalNegativeDeviation;
			totalNegativeDeviation = 0;
			for (Vegetable v : vegList) {
				if (v.getActualDeviationInShare() < 0) {
					totalNegativeDeviation += v.getActualDeviationInShare();
					if (totalPositiveDeviation < random) {
						swapTo = v;
						break;
					}
				}
			}

			System.out.println("The vegetables that are swappign rows are "
					+ swapFrom + "--> " + swapTo
					+ ". The unit of rows that are being swapped are "
					+ minRowLength);

			boolean swapFromSuccessful = false;

			for (Vegetable veg : vegList) {
				for (Variety v : veg.getVarieties()) {
					if (swapFrom.getVarieties().contains(v)
							&& (v.getActualRowLength() > v.getMinimumQuantity())
							&& ((v.getActualRowLength() - minRowLength) > v
									.getMinimumQuantity())) {

						v.setActualRowLength(v.getActualRowLength()
								- minRowLength);
						swapFromSuccessful = true;
					}
				}
			}

			if (swapFromSuccessful) {
				for (Vegetable veg : vegList) {
					for (Variety v : veg.getVarieties()) {
						if (swapTo.getVarieties().contains(v)) {
							v.setActualRowLength(v.getActualRowLength()
									+ minRowLength);
							swapped = true;
						}
					}
				}
			}
		}
		
		return swapFrom + "=>" + swapTo; 
	}
}
