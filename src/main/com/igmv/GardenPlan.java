package main.com.igmv;

import java.util.HashSet;
import java.util.Set;

public class GardenPlan {
	private int userExp;
	private double minRowlengthToSwap = 0.25;
	
	private final GardenSize size;
	private Set<IGMVVegetable> vegetables;
	
	public GardenPlan(int exp, GardenSize size, Set<IGMVVegetable> vegetables) {
		this.size = size;
		this.userExp = exp;
		this.vegetables = vegetables;

		double totalRowLength = size.getTotalRowLength();	
		
		int numPerson = size.getNumPersons();
		/**
		 * Get all the required vegetables.
		 */
		Set<IGMVVegetable> requiredVegs = new HashSet<IGMVVegetable>();
		for (IGMVVegetable veg : this.vegetables) {
			if (veg.isRequiredItem()) {
				requiredVegs.add(veg);
			}
		}
		calculateMinSwapLength();

		// Calculate the minimum row length required to grow required vegetables.		
		double minRowLength = 0;
		for (IGMVVegetable veg : requiredVegs) {
			double minLength = veg.getVarietyWithMinRowLength()
			.getMinimumRowLength() * numPerson * veg.getMinQuantity();
			System.out.println("For veg: " + veg.getName() + " required is " + minLength);
			minRowLength += minLength; 
		}
		System.out.println("total " + totalRowLength + " min " + minRowLength);
		if (minRowLength > totalRowLength) {
			throw new IllegalStateException(
					"Garden cannot be created. Minimum row length required to create garden is "
							+ minRowLength + ", whereas totalRowLength is "
							+ totalRowLength);
		}
		// First allocate least minimum required to grow required vegetables.
		double leftRowLength = totalRowLength;
		for (IGMVVegetable veg : requiredVegs) {
			IGMVVariety var = veg.getVarietyWithMinRowLength();
			double minLength = var.getMinimumRowLength() * numPerson
					* veg.getMinQuantity();
			addRowLengthToVariety(var, minLength);
			leftRowLength -= minLength;
		}
		// now allocate leftRowLength to vegetables/variety
		boolean added = false;
		while (leftRowLength > 0 && !added) {
			added = true;
			for (IGMVVegetable veg : this.vegetables) {
				for (IGMVVariety var : veg.getVarieties()) {
					if (leftRowLength > var.getMinimumRowLength()) {
						added = false;
						addRowLengthToVariety(var, var.getMinimumRowLength());
						leftRowLength -= var.getMinimumRowLength();
					}
				}
			}
		}
		
		if(leftRowLength > 0) {
			for (IGMVVegetable veg : this.vegetables) {
				for (IGMVVariety var : veg.getVarieties()) {
					if(var.getActualRowLength() > 0) {
						addRowLengthToVariety(var, leftRowLength);
						leftRowLength = 0;
						break;
					}
				}
			}	
		}
		
		System.out.println("The plan is valid " + isValid());
	}

	public GardenPlan(GardenPlan other) {
		this.userExp = other.userExp;
		this.minRowlengthToSwap = other.minRowlengthToSwap;
		this.size = other.size;
		this.vegetables = new HashSet<IGMVVegetable>();
		
		for (IGMVVegetable veg : other.vegetables) {
			this.vegetables.add(new IGMVVegetable(veg));
		}
	}
	
	public Set<IGMVVegetable> getVegetables() {
		return this.vegetables;
	}

	public int getUserExp() {
		return userExp;
	}

	public void setMinRowlengthToSwap(double minRowlengthToSwap) {
		this.minRowlengthToSwap = minRowlengthToSwap;
	}

	public double getMinRowlengthToSwap() {
		return minRowlengthToSwap;
	}
	
	public double getTotalRowLength() {
		return this.getSize().getNumRows() * this.getSize().getWidth();
	}

	public GardenSize getSize() {
		return size;
	}
	
	public static void addRowLengthToVariety(IGMVVariety var, double rowLength) {
		double currentRowLength = var.getActualRowLength();
		var.setActualRowLength(currentRowLength + rowLength);
	}
	public static void subRowLengthToVariety(IGMVVariety var, double rowLength) {
		double currentRowLength = var.getActualRowLength();
		var.setActualRowLength(currentRowLength - rowLength);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (IGMVVegetable veg : this.vegetables) {
			buf.append("\nVeg : " + veg.getName() + "\n" +
					" Required: " + veg.isRequiredItem() + "\n" +
					" MinQuantity: " + veg.getMinQuantity() + "\n" +
					" OptimalQuantity: " + veg.getOptimalQuantity() + "\n" +
					" MaxQuantity: " + veg.getMaxQuantity() + "\n" +
					" ActualQuantity: " + veg.getActualVegetableQuantity() + "\n");
			for (IGMVVariety var : veg.getVarieties()) {
				buf.append("\t Variety : " + var.getVarietyName() + "\n"
						+ "\t MinimumRowLengthRequired :" + var.getMinimumRowLength() + "\n" +
						 "\t Min Exp :" + var.getRequiredExp() + "\n"+
						"\t ActualRowLength : " + var.getActualRowLength());
				buf.append("\n\n");
			}
		}

		return buf.toString();
	}

	public boolean isValid() {
		Set<IGMVVegetable> requiredVegs = new HashSet<IGMVVegetable>();
		for (IGMVVegetable veg : this.vegetables) {
			if (veg.isRequiredItem()) {
				requiredVegs.add(veg);
			}
		}
		for (IGMVVegetable veg : requiredVegs) {
			if (veg.getActualVegetableQuantity() <= 0) {
				return false;
			}
		}
		
		double actualRowLength = 0;
		
		for(IGMVVegetable veg:this.vegetables) {
			for(IGMVVariety var: veg.getVarieties()) {
				actualRowLength += var.getActualRowLength();
			}
		}
		
		if (size.getTotalRowLength() != Math.round((actualRowLength * 100)/100)) {
			System.out.println("Total Row lenght " + size.getTotalRowLength()
					+ "actual allocated " + Math.round(actualRowLength * 100)/100);
			return false;
		}
		
		return true;
	}

	public Set<IGMVVariety> getVarieties() {
		Set<IGMVVariety> varieties = new HashSet<IGMVVariety>();

		for (IGMVVegetable veg : this.vegetables) {
			varieties.addAll(veg.getVarieties());
		}
		return varieties;
	}	
	
	private void calculateMinSwapLength() {
		
		Set<IGMVVariety> varieties = new HashSet<IGMVVariety>();
		for (IGMVVegetable veg : this.vegetables) {
			varieties.addAll(veg.getVarieties());
		}
		// Calculate the unit of swap
		for (IGMVVariety v : varieties) {
			if (getMinRowlengthToSwap() > v.getMinimumRowLength()) {
				setMinRowlengthToSwap(v.getMinimumRowLength());
			}
		}
	}
	
}
