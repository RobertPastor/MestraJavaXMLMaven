package com.issyhome.JavaMestra.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;

public class ExcelFormatsConverter {
	
	/**
	 * To convert an .xlsx file to an .xlsm file using Apache POI, you need to handle the workbook format properly and ensure that macros (if any) are preserved.
	 * Apache POI supports .xlsm files through the XSSFWorkbook and XSSFWorkbookFactory classes. Below is an example of how you can achieve this:
	 */

	/**
	 * 	Code Example
	 */
		
	/**

		Key Points:
		Macro Support: Apache POI does not create or modify macros. 
		If the .xlsx file contains macros, they will be preserved during the conversion, but you cannot add or edit macros using POI.
		Dependencies: Ensure you have the Apache POI library and its dependencies (like poi-ooxml) in your project.
		File Format: The .xlsm file is essentially an .xlsx file with macro support. The above code simply saves the workbook in .xlsm format.
		Maven Dependency

		Add the following dependencies to your pom.xml if you're using Maven:

		Copier le code
		<dependency>
		    <groupId>org.apache.poi</groupId>
		    <artifactId>poi-ooxml</artifactId>
		    <version>5.2.3</version> <!-- Use the latest version -->
		</dependency>
		<dependency>
		    <groupId>org.apache.commons</groupId>
		    <artifactId>commons-collections4</artifactId>
		    <version>4.4</version>
		</dependency>


		This approach ensures a seamless conversion while maintaining the integrity of the workbook.
	**/

	
	public static String ConvertXlsxToXlsm( final String inputFilePath ) {
		
		if ( ! inputFilePath.endsWith(".xlsx") ) {
			System.err.println("Input file expected extension shall be .xlsx");
			return null;
		}
		
		String outputFilePath = inputFilePath.split("\\.")[0] + ".xlsm";
		
		try {
        	FileInputStream fis = new FileInputStream(inputFilePath);
        	OPCPackage pkg = OPCPackage.open(fis);
        		
            XSSFWorkbook workbook =  XSSFWorkbookFactory.createWorkbook( pkg );
            //XSSFWorkbook workbook = new XSSFWorkbook ( pkg );
        	
        	// Save the workbook as .xlsm
        	workbook.setWorkbookType(XSSFWorkbookType.XLSM);
            FileOutputStream fos = new FileOutputStream(outputFilePath);

            workbook.write(fos);
            workbook.close();
            fos.close();
            System.out.println("Conversion from XLSX to XLSM completed successfully!");
            System.out.println("File output path = "+ outputFilePath);
            return outputFilePath;

        } catch (IOException e) {
            System.err.println("Error during conversion: " + e.getMessage());
        } catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public static String ConvertXlsmToXlsx( final String inputFilePath ) {
		
		System.out.println("Input file path = "+ inputFilePath);
		if ( ! inputFilePath.endsWith(".xlsm") ) {
			System.err.println("Input file expected extension shall be .xlsm");
			return null;
		}
		
		String outputFilePath = inputFilePath.split("\\.")[0] + ".xlsx";

		try {
        	FileInputStream fis = new FileInputStream(inputFilePath);
        	OPCPackage pkg = OPCPackage.open(fis);
        	
        	XSSFWorkbook workbook =  XSSFWorkbookFactory.createWorkbook( pkg );
            //XSSFWorkbook workbook = new XSSFWorkbook ( pkg );
        	
        	//get and remove the vbaProject.bin part from the package
        	PackagePart vbapart = pkg.getPartsByName(Pattern.compile("/xl/vbaProject.bin")).get(0);
        	  pkg.removePart(vbapart);

        	//get and remove the relationship to the removed vbaProject.bin part from the package
        	PackagePart wbpart = workbook.getPackagePart();
        	PackageRelationshipCollection wbrelcollection = wbpart.getRelationshipsByType("http://schemas.microsoft.com/office/2006/relationships/vbaProject");
        	for (PackageRelationship relship : wbrelcollection) {
        	   wbpart.removeRelationship(relship.getId());
        	}
        	
        	// Save the workbook as .xlsm
        	workbook.setWorkbookType(XSSFWorkbookType.XLSX);
            FileOutputStream fos = new FileOutputStream(outputFilePath);

            workbook.write(fos);
            workbook.close();
            fos.close();
            System.out.println("Conversion from XLSM to XLSX completed successfully!");
            System.out.println("File output path = "+ outputFilePath);
            return outputFilePath;

        } catch (IOException e) {
            System.err.println("Error during conversion: " + e.getMessage());
        } catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\rober\\OneDrive\\Bureau\\TestExcelFormatConversion.xlsx";  // Path to the .xlsx file
        //String outputFilePath = "C:\\Users\\rober\\OneDrive\\Bureau\\TestExcelFormatConversion.xlsm"; // Path to save the .xlsm file
        
        String outputFilePath = ExcelFormatsConverter.ConvertXlsxToXlsm ( inputFilePath );
        System.out.println( outputFilePath );
        
        inputFilePath =  "C:\\Users\\rober\\OneDrive\\Bureau\\TestExcelFormatConversion - Copie.xlsm";
        outputFilePath = ExcelFormatsConverter.ConvertXlsmToXlsx( inputFilePath );
        System.out.println( outputFilePath );
    }
}
