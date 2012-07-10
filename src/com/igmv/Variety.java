package com.igmv;

public class Variety {

	private String vegName;

	/**
	 * minimal experience required to grow it – this is the experience of the
	 * user
	 */
	private int requiredExp;
	/**
	 * defined as minimal row length required to grow it (strict restriction);
	 * if less space is available for this vegetable, then it should not be
	 * included in the garden
	 */
	private double minimumRowLength;
	
	private double actualRowLength;
	
	private double minimumShare;
	/**
	 * Name of the variety.
	 */
	private String varietyName;
	
	private double varietyShare;

	public Variety(String name, String veg, int minExp, double rowLength) {
		this.varietyName = name;
		this.vegName = veg;
		this.minimumRowLength = rowLength;
		this.requiredExp = minExp;
	}

	public String getVegetableName() {
		return vegName;
	}

	public int getRequiredExp() {
		return requiredExp;
	}

	public double getMinimumQuantity() {
		return minimumRowLength;
	}

	public String getVarietyName() {
		return varietyName;
	}

	public double getVarietyShare() {
		return varietyShare;
	}
	
	@Override
	public String toString() {
		return "\n VarietyName = " + this.varietyName + "\n" 
				+ " VegetableName = " + this.vegName + "\n"
				+ " Min. Exp. = " + this.requiredExp + "\n"
				+ "Min. Rowlength = " + this.minimumRowLength + "\n"+
				"VarietyShare = " + this.varietyShare + "\n";

	}

	public void setMinimumShare(double minimumShare) {
		this.minimumShare = minimumShare;
	}

	public double getMinimumShare() {
		return minimumShare;
	}

	public void setActualRowLength(double actualRowLength) {
		this.actualRowLength = actualRowLength;
	}

	public double getActualRowLength() {
		return actualRowLength;
	}
	
}