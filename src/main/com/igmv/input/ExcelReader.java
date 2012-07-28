package main.com.igmv.input;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import main.com.igmv.GardenSize;
import main.com.igmv.GardenType;
import main.com.igmv.Variety;
import main.com.igmv.Vegetable;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ExcelReader {

	public static Set<Variety> readData(String inputFile, GardenSize size) throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		Set<Variety> varieties = new HashSet<Variety>();
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// 0th row has column name
			// e.g Vegetable, Variety, Min.Experience, Min. row length
			for (int i = 1; i < sheet.getRows(); i++) {
				String group = sheet.getCell(0, i).getContents();
				if (group == "") {
					continue;
				}
				String name = sheet.getCell(1, i).getContents();
				int minExp = Integer.valueOf(sheet.getCell(2, i).getContents());
				double rowLength = (Double.valueOf(sheet.getCell(3, i)
						.getContents()) ) ;

				varieties.add(new Variety(name, group, minExp, rowLength, size));
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

		return varieties;
	}

	public static GardenType readGardenType(String gardenTypeFile, GardenSize size)
			throws IOException {
		GardenType type = new GardenType();
		File inputWorkbook = new File(gardenTypeFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(1);
			// 0th row has column name
			// e.g Vegetable, Variety, Min.Experience, Min. row length
			for (int i = 5; i < sheet.getRows(); i++) {
				String vegName = sheet.getCell(1, i).getContents();
				if (vegName == "") {
					continue;
				}
				boolean required = sheet.getCell(2, i).getContents()
						.equalsIgnoreCase("YES") ? true : false;
				int index = Integer.valueOf(sheet.getCell(3, i).getContents());
				double weight = Double.valueOf(sheet.getCell(4, i)
						.getContents());

				type.addVegetable(new Vegetable(vegName, weight, index,
						required, size));
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

		return type;
	}
	
	/**
	 * For testing purposes.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Set<Variety> varieties = ExcelReader.readData("res/data.xls", new GardenSize(10,10));
		System.out.println("The total number of varieties read are "
				+ varieties.size());
	}
	

}
