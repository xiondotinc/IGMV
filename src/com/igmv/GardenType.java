package com.igmv;

import java.util.HashSet;
import java.util.Set;

public class GardenType {
	private Set<Vegetable> vegetables;
	
	public GardenType() {
		this.vegetables = new HashSet<Vegetable>();
	}
	
	public void addVegetable(Vegetable veg) {
		double totalShare = 0;
		for (Vegetable v : this.getVegetables()) {
			totalShare += v.desiredShare;
		}
		if (totalShare + veg.desiredShare > 100) {
			throw new IllegalStateException(
					"Can't add more than 100 share of vegetables");
		}
		this.getVegetables().add(veg);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(Vegetable veg: getVegetables()) {
			buf.append("Veg: " + veg + "\n");
		}
		return buf.toString();
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		for (Vegetable v : this.vegetables) {
			hashCode += v.hashCode();
		}
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if (obj == this) return true;
		if (this.getClass() != obj.getClass()) return false;
		
		if(!(obj instanceof GardenType)) 
			return false;
		GardenType type = (GardenType)obj;
		
		if(!type.getVegetables().equals(this.vegetables)) {
			return false;
		}
		return true;
	}

	public Set<Vegetable> getVegetables() {
		return vegetables;
	}
	
}
