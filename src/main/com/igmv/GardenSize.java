package main.com.igmv;

public class GardenSize {
	private int length;
	private int width;
	private int numPerson;
	
	private int numRows;

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}
	
	public double getRowWidth() {
		return Row.width;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumPersons() {
		return this.numPerson;
	}
	
	public GardenSize(int length, int width, int numPerson) {
		if (length > width) {
			this.length = length;
			this.width = width;
		} else {
			this.length = width;
			this.width = length;
		}
		this.numRows = (int) (this.length / (Row.width + 2 * Row.margin));
		this.numPerson = numPerson;
	}

	private static class Row {
		static double width = 0.5;
		static double margin = 0.25;
	}

	/**
	 * for testing purposes.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GardenSize g = new GardenSize(700, 400, 10);
		System.out.println("The lenght of Garden is " + g.length + " width "
				+ g.width + " numRows " + g.numRows + " numPersons " + g.numPerson );
	}

	public int getTotalRowLength() {
		return getNumRows() * getWidth();
	}
}
