package main.com.igmv;

import java.util.HashSet;
import java.util.Set;

public class GardenPlan {
	private int userExp;
	private double minRowlengthToSwap = 0.25;
	
	private final GardenSize size;
	private Set<Vegetable> vegetables;
	
	public GardenPlan(int exp, GardenSize size, Set<Vegetable> vegetables) {
		this.size = size;
		this.userExp = exp;
		this.vegetables = vegetables;

		double totalRowLength = size.getTotalRowLength();		
		/**
		 * Get all the required vegetables.
		 */
		Set<Vegetable> requiredVegs = new HashSet<Vegetable>();
		for (Vegetable veg : this.vegetables) {
			if (veg.isRequiredItem()) {
				requiredVegs.add(veg);
			}
		}
		calculateMinSwapLength();

		// Calculate the minimum row length required to grow required vegetables.		
		double minRowLength = 0;
		for (Vegetable veg : requiredVegs) {
			minRowLength += veg.getVarietyWithMinRowLength()
					.getMinimumRowLength();
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
		for (Vegetable veg : requiredVegs) {
			Variety var = veg.getVarietyWithMinRowLength();
			addRowLengthToVariety(var, var.getMinimumRowLength());
			leftRowLength -= var.getMinimumRowLength();
		}
		// now allocate leftRowLength to vegetables/variety
		boolean added = false;
		while (leftRowLength > 0 && !added) {
			added = true;
			for (Vegetable veg : this.vegetables) {
				for (Variety var : veg.getVarieties()) {
					if (leftRowLength > var.getMinimumRowLength()) {
						added = false;
						addRowLengthToVariety(var, var.getMinimumRowLength());
						leftRowLength -= var.getMinimumRowLength();
					}
				}
			}
		}
		
		if(leftRowLength > 0) {
			for (Vegetable veg : this.vegetables) {
				for (Variety var : veg.getVarieties()) {
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
		this.vegetables = new HashSet<Vegetable>();
		
		for (Vegetable veg : other.vegetables) {
			this.vegetables.add(new Vegetable(veg));
		}
	}
	
	public Set<Vegetable> getVegetables() {
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
	
	public static void addRowLengthToVariety(Variety var, double rowLength) {
		double currentRowLength = var.getActualRowLength();
		var.setActualRowLength(currentRowLength + rowLength);
	}
	public static void subRowLengthToVariety(Variety var, double rowLength) {
		double currentRowLength = var.getActualRowLength();
		var.setActualRowLength(currentRowLength - rowLength);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Vegetable veg : this.vegetables) {
			buf.append("\nVeg : " + veg.getName() + "\n" +
					" Required: " + veg.isRequiredItem() + "\n" +
					" DesiredShare: " + veg.getDesiredShare() + "\n" +
					" ActualShare: " + veg.getActualVegetableShare() + "\n");
			for (Variety var : veg.getVarieties()) {
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
		Set<Vegetable> requiredVegs = new HashSet<Vegetable>();
		for (Vegetable veg : this.vegetables) {
			if (veg.isRequiredItem()) {
				requiredVegs.add(veg);
			}
		}
		for (Vegetable veg : requiredVegs) {
			if (veg.getActualVegetableShare() <= 0) {
				return false;
			}
		}
		
		double actualRowLength = 0;
		
		for(Vegetable veg:this.vegetables) {
			for(Variety var: veg.getVarieties()) {
				actualRowLength += var.getActualRowLength();
			}
		}
		
		if (size.getTotalRowLength() != actualRowLength) {
			System.out.println("Total Row lenght " + size.getTotalRowLength()
					+ "actual allocated " + actualRowLength);
			return false;
		}
		
		return true;
	}

	public Set<Variety> getVarieties() {
		Set<Variety> varieties = new HashSet<Variety>();

		for (Vegetable veg : this.vegetables) {
			varieties.addAll(veg.getVarieties());
		}
		return varieties;
	}	
	
	private void calculateMinSwapLength() {
		
		Set<Variety> varieties = new HashSet<Variety>();
		for (Vegetable veg : this.vegetables) {
			varieties.addAll(veg.getVarieties());
		}
		// Calculate the unit of swap
		for (Variety v : varieties) {
			if (getMinRowlengthToSwap() > v.getMinimumRowLength()) {
				setMinRowlengthToSwap(v.getMinimumRowLength());
			}
		}
	}
	
}
