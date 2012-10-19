package main.com.igmv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import main.com.igmv.search.Successor;
import main.com.igmv.search.SuccessorFunction;


public class GardenPlanSuccessorFunction implements SuccessorFunction {

	//Set<Vegetable> allVegetables;
	public GardenPlanSuccessorFunction() {
		//this.allVegetables = veg;
	}
	
	@Override
	public List getSuccessors(Object state) {
		
		GardenPlan newPlan = new GardenPlan((GardenPlan) state);
//		System.out.println("The old plan is " + state);
//		System.out.println("The new plan is " + newPlan);
		
		List<IGMVVegetable> l = new ArrayList<IGMVVegetable>();
		l.addAll(newPlan.getVegetables());
		Collections.sort(l);
		//System.out.println(" The sorted list of vegetable is : " + l  + " total size " + l.size() + " \n");
		String action = swapVegetables(l, newPlan.getMinRowlengthToSwap());
//		
		while (action!= null && !newPlan.isValid()) {
			action = swapVegetables(l, newPlan.getMinRowlengthToSwap());
		}
		
//		Set<Vegetable> successorVegSet = new HashSet<Vegetable>();
//		successorVegSet.addAll(l);

		List<Successor> successors = new ArrayList<Successor>();
		successors.add(new Successor(action, newPlan));

		return successors;
	}

	/**
	 * 1. Find all the varieties
	 * 2. Choose a variety to swap to, call it swapTo
	 *    if swapTo.rowLength==0
	 *      then choose varieties which contribute minRowLength till swapTo.minRequiredLength is reached 
	 * 	  else
	 *      choose a variety to swapFrom with minRowLength 
	 *  
	 *  Consider user's experience and experience required to grow a variety while choosing
	 *  e.g. 
	 * @param vegList
	 * @param minRowLength
	 * @return
	 */
	
	
	private String swapVegetables(List<IGMVVegetable> vegList, double minRowLength) {

		List<IGMVVariety> varieties = new ArrayList<IGMVVariety>();
		for (IGMVVegetable veg : vegList) {
			varieties.addAll(veg.getVarieties());
		}
		Collections.shuffle(varieties);
		for (IGMVVariety varietyTo : varieties) {
			Set<SwapUnit> unitsFrom = new HashSet<SwapUnit>();
			
			if (varietyTo.getActualRowLength() == 0) {
				double requiredLength = varietyTo.getMinimumRowLength();
				double availableLength = 0;
				boolean unitsMet = true;
				while (availableLength < requiredLength) {
					SwapUnit unitFrom = findVarietyToSwapFrom(varieties,
							minRowLength);
					if (unitFrom == null) {
						unitsMet = false;
						break;
					}
					unitsFrom.add(unitFrom);
					availableLength += unitFrom.length;
				}
				if (!unitsMet) {
					unitsFrom.clear();
					continue;
				}
			} else {
				SwapUnit unitFrom = findVarietyToSwapFrom(varieties,
						minRowLength);
				if (unitFrom == null) {
					continue;
				}
				unitsFrom.add(unitFrom);
			}
			System.out.println("VarietyTo " + varietyTo + " \n UnitsFrom " + unitsFrom);
			if (unitsFrom != null && !unitsFrom.isEmpty()) {
				doSwapping(varietyTo, unitsFrom);
				return unitsFrom + "=>" + varietyTo;
			}
		}
		return null;
		
	}
	
	private void doSwapping(IGMVVariety varietyTo, Set<SwapUnit> unitsFrom) {
		for (SwapUnit unit : unitsFrom) {
			GardenPlan.subRowLengthToVariety(unit.var, unit.length);
			GardenPlan.addRowLengthToVariety(varietyTo, unit.length);
		}
	}

//	private Variety findVarietyToSwapTo(List<Variety> varieties) {
//		Variety v = null;
//		Collections.shuffle(varieties);
//		if (!varieties.isEmpty())
//			v = varieties.get(0);
//
//		return v;
//	}
	
	/**
	 * Don't give a variety which after removing will cause the vegetable also to remove from the plan.
	 * @param varieties
	 * @param minRowLength
	 * @return
	 */
	private SwapUnit findVarietyToSwapFrom(List<IGMVVariety> varieties,
			double minRowLength) {
		List<IGMVVariety> temp = new ArrayList<IGMVVariety>();
		for (IGMVVariety v : varieties) {
			if (v.getActualRowLength() > 0)
				temp.add(v);
		}
		Collections.shuffle(temp);
		SwapUnit unit = null;

		for (IGMVVariety v : temp) {
			if (v.getActualRowLength() - minRowLength < v.getMinimumRowLength()
					&& ((v.getActualRowLength() - minRowLength) > 0)) {
				if (v.getActualRowLength() - v.getMinimumRowLength() < v
						.getMinimumRowLength()
						&& ((v.getActualRowLength() - v.getMinimumRowLength()) > 0)) {
					unit = new SwapUnit(v, v.getMinimumRowLength());
				}
			} else {
				unit = new SwapUnit(v, minRowLength);
			}

			if (unit != null)
				return unit;
		}
		return unit;

	}

	
	class SwapUnit {
		private IGMVVariety var;
		private double length;
		
		public SwapUnit(IGMVVariety var, double length) {
			this.var = var;
			this.length = length;
		}
		
		@Override
		public String toString() {
			return "Var :" + this.var + " \n" + " length " + this.length + "\n";
		}
	}
	
	private String swapVegetablesOld(List<IGMVVegetable> vegList, double minRowLength) {
		
		boolean swapped = false;
		IGMVVegetable swapFrom = null, swapTo = null;
		while (!swapped) {
			double totalPositiveDeviation = 0, totalNegativeDeviation = 0;
			for (IGMVVegetable v : vegList) {
				if (v.getActualDeviationInShare() > 0) {
					totalPositiveDeviation += v.getActualDeviationInShare();
				}
				if (v.getActualDeviationInShare() < 0) {
					totalNegativeDeviation += v.getActualDeviationInShare();
				}
			}
			
			System.out.println("Total positive deviation in share "
					+ totalPositiveDeviation
					+ " , total negative deviation in share is : "
					+ totalNegativeDeviation);
			
			if(totalPositiveDeviation > IGMV.tolerance) {
				//findVegetableToSwapTo();
				//findVegetableToSwapFrom();
			}
			else {
				// need to exchange within vegetables and try adding more variety
				
			}
			
			
			Random r = new Random();
			double random = r.nextDouble() * totalPositiveDeviation;
			totalPositiveDeviation = 0;
			
			for (IGMVVegetable v : vegList) {
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
			Collections.reverse(vegList);
			for (IGMVVegetable v : vegList) {
				if (v.getActualDeviationInShare() < 0) {
					totalNegativeDeviation += v.getActualDeviationInShare();
					if (totalNegativeDeviation < random) {
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

			for (IGMVVegetable veg : vegList) {
				for (IGMVVariety v : veg.getVarieties()) {
					if (swapFrom.getVarieties().contains(v)
							&& (v.getActualRowLength() > v.getMinimumRowLength())
							&& ((v.getActualRowLength() - minRowLength) > v
									.getMinimumRowLength())) {

						GardenPlan.subRowLengthToVariety(v, minRowLength);	
						swapFromSuccessful = true;
					}
				}
			}

			if (swapFromSuccessful) {
				for (IGMVVegetable veg : vegList) {
					for (IGMVVariety v : veg.getVarieties()) {
						if (swapTo.getVarieties().contains(v)) {
							GardenPlan.addRowLengthToVariety(v, minRowLength);
							swapped = true;
						}
					}
				}
			}
		}
		
		return swapFrom + "=>" + swapTo; 
	}
	
	
}
