package main.com.igmv;

import java.util.HashSet;
import java.util.Set;

public class IGMVVegetable implements Comparable<IGMVVegetable> {
	
	private Set<IGMVVariety> varieties;
	/**
	 * (length of row planted with vegetable)/(sum of lengths of all rows)
	 */
	private double optimalQuantity;
	
	private double minQuantity;
	
	private double maxQuantity;
	
	/**
	 * has values 1 to 10 – 10 is the most desired and 1 is at the bottom of the list
	 * if we do not have enough space to include all the vegetables, we exclude
	 * those with the lowest index of desirability (if more with same then random)
	 */
	int desirabilityIndex;
	
	/**
	 * If true then it has to be included in the Garden (Strict Restriction) 
	 */
	boolean requiredItem;
	
	String name;
	
	private GardenSize size;
	
	public IGMVVegetable(String name, double minQuantity, double optimalQuantity, double maxQuantity, int index, boolean required, GardenSize size) {
		this.varieties = new HashSet<IGMVVariety>();
		this.name = name;
		this.requiredItem = required;
		this.desirabilityIndex = index;
		this.minQuantity = minQuantity;
		this.optimalQuantity = optimalQuantity;
		this.maxQuantity = maxQuantity;
		this.size = size;
	}
	
	public IGMVVegetable(IGMVVegetable other) {
		this.name = other.name;
		this.requiredItem = other.requiredItem;
		this.desirabilityIndex = other.desirabilityIndex;
		this.minQuantity = other.minQuantity;
		this.optimalQuantity = other.optimalQuantity;
		this.maxQuantity = other.maxQuantity;
		this.size = other.size;
		this.varieties = new HashSet<IGMVVariety>();
		for (IGMVVariety v : other.varieties) {
			this.varieties.add(new IGMVVariety(v));
		}
	}

	public double getActualVegetableQuantity() {
		int numQuantity = 0;
		//double rowLength = 0;
		for (IGMVVariety v : this.varieties) {
			numQuantity += Math.round((100 * v.getActualRowLength() / v
					.getMinimumRowLength()) / 100);
		}
		//int totalRowLength = size.getLength() * size.getNumRows();
		//return 100 * (rowLength/totalRowLength);
		return numQuantity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof IGMVVegetable)) return false;
		IGMVVegetable v = (IGMVVegetable)obj;
		if(!v.name.equalsIgnoreCase(this.name)) return false;
		//if(v.getActualVegetableShare() != this.getActualVegetableShare()) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.name.toLowerCase().hashCode();// + (int) getActualVegetableShare();
	}
	
	public Set<IGMVVariety> getVarieties() {
		return varieties;
	}

	public double getOptimalQuantity() {
		return optimalQuantity;
	}

	public int getDesirabilityIndex() {
		return desirabilityIndex;
	}

	public boolean isRequiredItem() {
		return requiredItem;
	}

	public String getName() {
		return this.name;
	}

	public double getDeviationInShare() {
		return Math.abs(this.optimalQuantity * size.getNumPersons()
				- this.getActualVegetableQuantity());
	}

	public double getPercentageDeviationInShare() {
		return 100 * (Math.abs(this.optimalQuantity * size.getNumPersons()
				- this.getActualVegetableQuantity()) / this.optimalQuantity);
	}

	public double getActualDeviationInShare() {
		return this.getActualVegetableQuantity() - this.optimalQuantity * size.getNumPersons();
	}
	
	public void addVariety(IGMVVariety v) {
		this.varieties.add(v);
	}
	
	@Override
	public String toString() {
		String s = "";
		for (IGMVVariety v : this.varieties)
			s += " " + v.toString() + "\n";

		return "Name: " + this.name + "\n" + "Required: " + this.requiredItem
				+ "\n" + "Desirability: " + this.desirabilityIndex + "\n"
				+ "OptimalQuantity: " + this.optimalQuantity + "\n" 
				+ "ActualShare: " + this.getActualVegetableQuantity() + "\n" +
				"Varieties : " + s;
	}
	
	public static void main(String[] args) {
		IGMVVegetable v1 = new IGMVVegetable("Apple", 34, 60, 90, 10, true, new GardenSize(100,70, 5));
		IGMVVegetable v2 = new IGMVVegetable("apple", 32, 60, 90, 10, true, new GardenSize(70,100, 5));
		System.err.println(v1.equals(v2));
		System.err.println(v1.hashCode() + "  " + v2.hashCode());
	}

	@Override
	public int compareTo(IGMVVegetable v) {
		if (this.getActualDeviationInShare() > v.getActualDeviationInShare()) {
			return 1;
		}
		if (this.getActualDeviationInShare() < v.getActualDeviationInShare()) {
			return -1;
		}
		return 0;
	}
	
	public IGMVVariety getVarietyWithMinRowLength() {
		IGMVVariety v = null;
		double rowLength = Integer.MAX_VALUE;
		for (IGMVVariety vr : this.varieties) {
			if (rowLength > vr.getMinimumRowLength()) {
				v = vr;
				rowLength = vr.getMinimumRowLength();
			}
		}
		return v;
	}

	public double getMinQuantity() {
		return minQuantity;
	}

	public double getMaxQuantity() {
		return maxQuantity;
	}

}
