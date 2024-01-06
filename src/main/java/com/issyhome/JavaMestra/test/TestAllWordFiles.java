package com.issyhome.JavaMestra.test;

import java.io.File;
import java.util.TreeSet;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.issyhome.JavaMestra.configuration.ConfigurationFileXLSReader;
import com.issyhome.JavaMestra.gui.StatusBar;
import com.issyhome.JavaMestra.mestra.MestraFileType;
import com.issyhome.JavaMestra.poi.WordFile;


public class TestAllWordFiles {


	/**
	 * Returns the list of files
	 * of the folder provided in parameter
	 * and filtered with the provided extension
	 */
	public static File [] searchFileFolder (String folder, String extension)
	{
		File file = new File (folder);
		//System.out.println(file.getAbsolutePath());
		
		/*
		 * if the folder does not exists
		 * or if it is a file, throw an exception
		 */
		if (  !file.exists() || !file.isDirectory() ) {
		    throw new IllegalArgumentException (file + " unknown");
		}
			
		
		// Recursive search of files from the folder
		TreeSet<File> sortedFiles = new TreeSet<File>();
		searchFileFolder (sortedFiles , file , extension);
		
		// Copy the file names in an array of the same size
		File [] files = new File [sortedFiles.size()];
		sortedFiles.toArray(files);
		return files;
	}

	private static void searchFileFolder (TreeSet<File> files,
			File folderPath, String extension)
	{
		//System.out.println(folderPath.getAbsolutePath());
		
		/*
		 * search sub folders 
		 * and files with the right extension
		 * 
		 */ 
		File [] filePath = folderPath.listFiles();
		for (int i = 0; i < filePath.length; i++) {
			// Si le chemin est un dossier
			//System.out.println("Current file/folder: "+ filePath[i].getAbsolutePath());
		
			if (filePath [i].isDirectory())
				// Recursively search of files from this folder
				searchFileFolder (files, filePath [i], extension);
			
		// Sinon ajout du fichier d'extension voulue a l'ensemble
			else if (filePath [i].getName().endsWith(extension)) {
				//System.out.println(filePath[i].getAbsolutePath());
				files.add (filePath [i]);
			}
		}
	}
	
    public static void main(String[] args) throws Exception {
    	System.out.println("FolderBrowser main starting");
    	
        String ExcelConfigurationFilePath;
        ExcelConfigurationFilePath = "D:/MESTRA TOOL/Mestra_Tool_V2_44 5_November_2007.xls";
        ConfigurationFileXLSReader configuration = new ConfigurationFileXLSReader(ExcelConfigurationFilePath);

    	String path = "D:/S2K/S2K2/";
        File[] files = searchFileFolder(path,"doc");
        int nb = files.length;
        System.out.println("number of files found: "+nb);
        for (int i = 0; i <nb; i++) {
        	System.out.println(i+" "+files[i].getAbsolutePath());
        	//System.out.println(files[i].getCanonicalPath());
        	WordFile aWordFile = new WordFile();
        	String LongFileName = files[i].getAbsolutePath();
        	XWPFDocument doc = aWordFile.open(LongFileName);
            if (doc != null) {
                StatusBar statusBar = null;
                
                aWordFile.ExtractMestraMarkers(doc, 
                        configuration.getMestraStyles(MestraFileType.MestraFileTypeEnum.SSS),
                        statusBar) ;
            }
            doc.close();
        }
    }

}

