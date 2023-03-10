package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	// in ExcelUtil we have to write a code to read from xl file.
	
	public static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	
	public static Object[][] getTestData(String sheetName ) {
		
		System.out.println("reading data from sheet: " + sheetName);
		
		Object data [][] = null;
		
		try {
			System.out.println("before fileinput stream");
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			System.out.println("after fileinput stream");
			book = WorkbookFactory.create(ip);  //create the file in java memeory
			System.out.println("after workbook create");
			sheet = book.getSheet(sheetName);
		
		
		    data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		    
		  for(int i=0; i<sheet.getLastRowNum(); i++) {
			  for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
				  data[i][j] = sheet.getRow(i+1).getCell(j).toString();  //i+1 because e don't need 1st row which have firstname, lastname
			  }
		  }
		    
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}

}


//WorkBookFactory --> from apoci poi
//Workbook from --> apoci poi
//create() method return Workbook
//getSheet() return Sheet
//in any webtable column count are same for all rows
//sheet.getrow(0)--> first row index startr with 0 