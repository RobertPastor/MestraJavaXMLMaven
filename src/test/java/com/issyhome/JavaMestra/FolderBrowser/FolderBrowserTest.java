package com.issyhome.JavaMestra.FolderBrowser;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.logging.Logger;

public class FolderBrowserTest {

	final static Logger logger = Logger.getLogger(FolderBrowser.class.getName()); 
	
	@Test
    void FolderBrowserTest001() {
 
		String folderPath = "C:\\Users\\rober\\.m2\\repository";
		FolderBrowser folderBrowser = new FolderBrowser(folderPath);
		
		logger.info("Folder '" + folderPath + "' is existing = " + String.valueOf(folderBrowser.FolderExists()));
		assertEquals(folderBrowser.FolderExists() , true);
		
		String[] fileExtensions = { "*.jar" };
		int count = 0;
		for (File file : folderBrowser.GetFilesInFolder( fileExtensions , true)) {
			logger.info(file.getAbsolutePath());
			count = count + 1;
		}
		logger.info("File count = " + count);
    }
	
}
