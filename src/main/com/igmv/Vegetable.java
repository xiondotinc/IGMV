package main.com.igmv;

import java.util.HashSet;
import java.util.Set;

public class Vegetable implements Comparable<Vegetable> {
	
	private Set<Variety> varieties;
	/**
	 * (length of row planted with vegetable)/(sum of lengths of all rows)
	 */
	double desiredShare;
	
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
	
	public Vegetable(String name, double share, int index, boolean required, GardenSize size) {
		this.varieties = new HashSet<Variety>();
		this.name = name;
		this.requiredItem = required;
		this.desirabilityIndex = index;
		this.desiredShare = share;
		this.size = size;
	}
	
	public Vegetable(Vegetable other) {
		this.name = other.name;
		this.requiredItem = other.requiredItem;
		this.desirabilityIndex = other.desirabilityIndex;
		this.desiredShare = other.desiredShare;
		this.size = other.size;
		this.varieties = new HashSet<Variety>();
		for (Variety v : other.varieties) {
			this.varieties.add(new Variety(v));
		}
	}

	public double getActualVegetableShare() {
		double rowLength = 0;
		for (Variety v : this.varieties) {
			rowLength += v.getActualRowLength();
		}
		int totalRowLength = size.getLength() * size.getNumRows();
		return 100 * (rowLength/totalRowLength);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Vegetable)) return false;
		Vegetable v = (Vegetable)obj;
		if(!v.name.equalsIgnoreCase(this.name)) return false;
		//if(v.getActualVegetableShare() != this.getActualVegetableShare()) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.name.toLowerCase().hashCode();// + (int) getActualVegetableShare();
	}
	
	public Set<Variety> getVarieties() {
		return varieties;
	}

	public double getDesiredShare() {
		return desiredShare;
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
		return Math.abs(this.desiredShare - this.getActualVegetableShare());
	}

	public double getPercentageDeviationInShare() {
		return 100 * (Math.abs(this.desiredShare
				- this.getActualVegetableShare()) / this.desiredShare);
	}

	public double getActualDeviationInShare() {
		return this.getActualVegetableShare() - this.desiredShare;
	}
	
	public void addVariety(Variety v) {
		this.varieties.add(v);
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Variety v : this.varieties)
			s += " " + v.toString() + "\n";

		return "Name: " + this.name + "\n" + "Required: " + this.requiredItem
				+ "\n" + "Desirability: " + this.desirabilityIndex + "\n"
				+ "DesiredShare: " + this.desiredShare + "\n" 
				+ "ActualShare: " + this.getActualVegetableShare() + "\n" +
				"Varieties : " + s;
	}
	
	public static void main(String[] args) {
		Vegetable v1 = new Vegetable("Apple", 34, 10, true, new GardenSize(100,70));
		Vegetable v2 = new Vegetable("apple", 32, 10, true, new GardenSize(70,100));
		System.err.println(v1.equals(v2));
		System.err.println(v1.hashCode() + "  " + v2.hashCode());
	}

	@Override
	public int compareTo(Vegetable v) {
		if (this.getActualDeviationInShare() > v.getActualDeviationInShare()) {
			return 1;
		}
		if (this.getActualDeviationInShare() < v.getActualDeviationInShare()) {
			return -1;
		}
		return 0;
	}
	
	public Variety getVarietyWithMinRowLength() {
		Variety v = null;
		double rowLength = Integer.MAX_VALUE;
		for (Variety vr : this.varieties) {
			if (rowLength > vr.getMinimumRowLength()) {
				v = vr;
				rowLength = vr.getMinimumRowLength();
			}
		}
		return v;
	}
}
