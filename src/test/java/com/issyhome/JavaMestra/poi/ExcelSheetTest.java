package com.issyhome.JavaMestra.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import com.issyhome.JavaMestra.FolderBrowser.FolderBrowser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.logging.Logger;

public class ExcelSheetTest {
	
	final static Logger logger = Logger.getLogger(FolderBrowser.class.getName()); 

	
	@Test
    void ExcelSheetTest001() {
		
		HSSFWorkbook wb = new HSSFWorkbook();
		
		String sheetName = "test Espace";
		logger.info(sheetName);
		ExcelSheet sheet = new ExcelSheet(wb, sheetName);
		logger.info( sheet.getSheetName() );
		
		sheetName = "test_underscore";
		logger.info(sheetName);
		sheet = new ExcelSheet(wb, sheetName);
		logger.info( sheet.getSheetName() );
		
		sheetName = "test(_parenthesis";
		logger.info(sheetName);
		sheet = new ExcelSheet(wb, sheetName);
		logger.info( sheet.getSheetName() );
		
		sheetName = "test_long_name_012345678901234567890123456789";
		logger.info(sheetName);
		sheet = new ExcelSheet(wb, sheetName);
		logger.info( sheet.getSheetName() );
		
		
	}
}
