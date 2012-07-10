package com.igmv;

import java.util.HashSet;
import java.util.Set;

public class Vegetable implements Comparable {
	private Set<Variety> varieties;
	/**
	 * (length of row planted with vegetable)/(sum of lengths of all rows)
	 */
	double desiredShare;
	
	double desiedRowlength;
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
	
	public Vegetable(String name, double share, int index, boolean required) {
		this.varieties = new HashSet<Variety>();
		this.name = name;
		this.requiredItem = required;
		this.desirabilityIndex = index;
		this.desiredShare = share;
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

	public double getActualVegetableShare() {
		double share = 0;
		for (Variety v : this.varieties) {
			share += v.getVarietyShare();
		}
		return share;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof Vegetable)) return false;
		Vegetable v = (Vegetable)obj;
		if(!v.name.equalsIgnoreCase(this.name)) return false;
		if(v.getActualVegetableShare() != this.getActualVegetableShare()) return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.name.toLowerCase().hashCode() + (int) getActualVegetableShare();
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
	
	public void addVariety(Variety v){
		this.varieties.add(v);
	}
	
	public static void main(String[] args) {
		Vegetable v1 = new Vegetable("Apple", 34, 10, true);
		Vegetable v2 = new Vegetable("apple", 32, 10, true);
		System.err.println(v1.equals(v2));
		System.err.println(v1.hashCode() + "  " + v2.hashCode());
	}

	@Override
	public int compareTo(Object o) {
		Vegetable v = (Vegetable) o;
		if (this.getActualDeviationInShare() > v.getActualDeviationInShare()) {
			return 1;
		}
		if (this.getActualDeviationInShare() < v.getActualDeviationInShare()) {
			return -1;
		}
		return 0;
	}
}
