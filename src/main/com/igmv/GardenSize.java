package main.com.igmv;

public class GardenSize {
	private int length;
	private int width;

	private int numRows;

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getNumRows() {
		return numRows;
	}

	public GardenSize(int length, int width) {
		if (length > width) {
			this.length = length;
			this.width = width;
		} else {
			this.length = width;
			this.width = length;
		}
		this.numRows = (int) (this.length / (Row.width + 2 * Row.margin));
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
		GardenSize g = new GardenSize(700, 400);
		System.out.println("The lenght of Garden is " + g.length + " width "
				+ g.width + " numRows " + g.numRows);
	}

	public int getTotalRowLength() {
		return getNumRows() * getWidth();
	}
}
