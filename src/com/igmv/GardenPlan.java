package com.igmv;

import java.util.HashSet;
import java.util.Set;

public class GardenPlan {
	private int userExp;
	private Set<Variety> varieties;
	private GardenSize size;
	private Set<Vegetable> vegetables;
	
	private double minRowlengthToSwap = 0;
	
	public GardenPlan(int exp, int length, int width, Set<Vegetable> vegetables) {
		this.setSize(new GardenSize(length, width));
		this.userExp = exp;
		this.varieties = new HashSet<Variety>();
		this.vegetables = vegetables;
		for (Vegetable veg : this.vegetables) {
			this.varieties.addAll(veg.getVarieties());
		}
		
		for(Variety v: this.varieties) {
			v.setMinimumShare(v.getMinimumQuantity()
					/ (this.getSize().getNumRows() * this.getSize().getWidth()));
			if (getMinRowlengthToSwap() > v.getMinimumQuantity()) {
				setMinRowlengthToSwap(v.getMinimumQuantity());
			}
		}
	}

	public Set<Variety> getVarieties() {
		return varieties;
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

	public void setSize(GardenSize size) {
		this.size = size;
	}

	public GardenSize getSize() {
		return size;
	}
}
