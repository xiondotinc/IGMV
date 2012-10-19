package main.com.igmv;

public class IGMVVariety {

	private String vegName;

	/**
	 * Name of the variety.
	 */
	private String varietyName;
	
	/**
	 * minimal experience required to grow it â this is the experience of the
	 * user
	 */
	private int requiredExp;
	/**
	 * defined as minimal row length required to grow it (strict restriction);
	 * if less space is available for this vegetable, then it should not be
	 * included in the garden
	 */
	private double minimumRowLength;
	
	private boolean lengthCrucial;
	private double unitLength;
	private double unitArea;
	
	private double actualRowLength;
	
	private int actualUnit;
	
	private GardenSize size;

	public IGMVVariety(String name, String veg, int minExp, boolean lengthCrucial,
			double unitLength, double unitArea, GardenSize size) {
		this.varietyName = name;
		this.vegName = veg;
		this.lengthCrucial = lengthCrucial;
		this.unitArea = unitArea;
		this.unitLength = unitLength;
		this.requiredExp = minExp;
		this.size = size;
		if (lengthCrucial) {
			this.minimumRowLength = unitLength;
		} else {
			this.minimumRowLength = unitArea / size.getRowWidth();
		}
	}

	public IGMVVariety(IGMVVariety other) {
		this.varietyName = other.varietyName;
		this.vegName = other.vegName;
		this.minimumRowLength = other.minimumRowLength;
		this.requiredExp = other.requiredExp;
		this.actualRowLength = other.actualRowLength;
		this.actualUnit = other.actualUnit;
		this.lengthCrucial = other.lengthCrucial;
		this.unitLength = other.unitLength;
		this.unitArea = other.unitArea;
		this.size = other.size;
	}
	
	public String getVegetableName() {
		return vegName;
	}

	public int getRequiredExp() {
		return requiredExp;
	}

	public double getMinimumRowLength() {
		return minimumRowLength;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public double getVarietyShare() {
		//calculate varietyshare from actualrowLength
		return (100 * this.actualRowLength / this.size.getTotalRowLength());
	}
	
//	public void setMinimumShare(double minimumShare) {
//		this.minimumShare = minimumShare;
//	}
//
//	public double getMinimumShare() {
//		return minimumShare;
//	}

	public void setActualRowLength(double actualRowLength) {
		this.actualRowLength = actualRowLength;
	}

	public double getActualRowLength() {
		return actualRowLength;
	}
	
	@Override
	public String toString() {
		return "\n VarietyName = " + this.varietyName + "\n" 
				+ " VegetableName = " + this.vegName + "\n"
				+ " Min. Exp. = " + this.requiredExp + "\n"
				+ "Min. Rowlength = " + this.minimumRowLength + "\n"+
				" Actual RowLength = " + this.getActualRowLength() + "\n";

	}
	
	
//	@Override
//	public boolean equals(Object obj) {
//		if(obj == null) return false;
//		if(!(obj instanceof Variety)) return false;
//		Variety v = (Variety)obj;
//		if(!v.varietyName.equalsIgnoreCase(this.varietyName)) return false;
//		//if(v.actualRowLength != this.actualRowLength) return false;
//		return true;
//	}
//	
//	@Override
//	public int hashCode() {
//		return this.varietyName.hashCode();
//	}
}