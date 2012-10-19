package main.com.igmv.input;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import main.com.igmv.GardenSize;
import main.com.igmv.IGMVGardenType;
import main.com.igmv.IGMVVariety;
import main.com.igmv.IGMVVegetable;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ExcelReader {

	public static Set<IGMVVariety> readData(String inputFile, GardenSize size) throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		Set<IGMVVariety> varieties = new HashSet<IGMVVariety>();
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
				String lengthCrucial = sheet.getCell(3, i).getContents();
				if (lengthCrucial.equalsIgnoreCase("TRUE")) {
					double unitLength = (Double.valueOf(sheet.getCell(4, i)
							.getContents()));
					varieties.add(new IGMVVariety(name, group, minExp, true,
							unitLength, -1, size));
				} else {
					double unitArea = (Double.valueOf(sheet.getCell(5, i)
							.getContents()));
					varieties.add(new IGMVVariety(name, group, minExp, false, -1,
							unitArea, size));
				}
				
			}

		} catch (BiffException e) {
			e.printStackTrace();
		}

		return varieties;
	}

	public static IGMVGardenType readGardenType(String gardenTypeFile, GardenSize size)
			throws IOException {
		IGMVGardenType type = new IGMVGardenType();
		File inputWorkbook = new File(gardenTypeFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
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
				double minQuantity = Double.valueOf(sheet.getCell(4, i)
						.getContents());
				double optimumQuantity = Double.valueOf(sheet.getCell(5, i)
						.getContents());
				double maxQuantity = Double.valueOf(sheet.getCell(6, i)
						.getContents());
				type.addVegetable(new IGMVVegetable(vegName, minQuantity, optimumQuantity, maxQuantity, index,
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
		Set<IGMVVariety> varieties = ExcelReader.readData("res/data.xls", new GardenSize(10,10, 5));
		System.out.println("The total number of varieties read are "
				+ varieties.size());
	}
	

}
