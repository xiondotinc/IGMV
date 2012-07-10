package com.igmv;

import java.io.IOException;
import java.util.Set;

import com.igmv.input.ExcelReader;

public class IGMV {

	private static String dataFile = "res/data.xls";
	private static String gardenTypeFile = "res/garden type.xls";

	public static void main(String[] args) throws IOException {
		GardenType type = ExcelReader.readGardenType(gardenTypeFile);
		Set<Variety> varieties = ExcelReader.readData(dataFile);
		System.out.println("The varieties are : \n" + varieties);

		for (Vegetable veg : type.getVegetables()) {
			boolean set = false;
			for (Variety v : varieties) {
				if (v.getVegetableName().equals(veg.getName())) {
					veg.addVariety(v);
				}
			}
		}

		System.out.println("The Garden type is : \n" + type);

		GardenPlanGoalTest goalTest = new GardenPlanGoalTest(type);
		GardenPlan plan = new GardenPlan(7, 100, 70, type.getVegetables());

		System.out.println(goalTest.isGoalState(plan));
	}
}
