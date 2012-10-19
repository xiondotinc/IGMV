package main.com.igmv;

import java.util.HashSet;
import java.util.Set;

public class IGMVGardenType {
	private Set<IGMVVegetable> vegetables;
	
	public IGMVGardenType() {
		this.vegetables = new HashSet<IGMVVegetable>();
	}
	
	public void addVegetable(IGMVVegetable veg) {
//		double totalShare = 0;
//		for (Vegetable v : this.getVegetables()) {
//			totalShare += v.optimalQuantity;
//		}
//		if (totalShare + veg.optimalQuantity > 100) {
//			throw new IllegalStateException(
//					"Can't add more than 100 share of vegetables");
//		}
		this.getVegetables().add(veg);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(IGMVVegetable veg: getVegetables()) {
			buf.append("Veg: " + veg + "\n");
		}
		return buf.toString();
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		for (IGMVVegetable v : this.vegetables) {
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
		
		if(!(obj instanceof IGMVGardenType)) 
			return false;
		IGMVGardenType type = (IGMVGardenType)obj;
		
		if(!type.getVegetables().equals(this.vegetables)) {
			return false;
		}
		return true;
	}

	public Set<IGMVVegetable> getVegetables() {
		return vegetables;
	}
	
}
