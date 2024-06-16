package com.issyhome.JavaMestra.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;


public class CreateLargeSSSTest {

	final static Logger logger = Logger.getLogger(CreateLargeSSSTest.class.getName()); 


	private static XWPFStyle createNamedStyle(XWPFStyles styles, STStyleType.Enum styleType, String styleId) {
		if (styles == null || styleId == null) return null;
		XWPFStyle style = styles.getStyle(styleId);
		if (style == null) {
			CTStyle ctStyle = CTStyle.Factory.newInstance();
			ctStyle.addNewName().setVal(styleId);
			ctStyle.xsetCustomStyle(null);
			style = new XWPFStyle(ctStyle, styles);
			style.setType(styleType);
			style.setStyleId(styleId);
			styles.addStyle(style);
		}
		return style;
	}


	@Test
	void CreateLargeSSSTestWithStyles() {

		try {
			XWPFDocument document = new XWPFDocument();
			XWPFStyles styles = document.createStyles();
			XWPFStyle xwpfStyleRequirement = createNamedStyle(styles, STStyleType.PARAGRAPH, "M:T:PUID:reqt");
			XWPFStyle xwpfStyleChanges = createNamedStyle(styles, STStyleType.PARAGRAPH, "M:T:AT:categ");
			
			for (int i = 0 ; i < 2000 ; i++ ) {
				
				XWPFParagraph paragraph = document.createParagraph();
				paragraph.setStyle(xwpfStyleRequirement.getStyleId());
				
				XWPFRun run = paragraph.createRun();
				run.setText("SSS_REQ_" + String.format("%05d", i));
				run.setFontSize(12);
				
				paragraph = document.createParagraph();
				paragraph.setStyle(xwpfStyleChanges.getStyleId());
				
				run = paragraph.createRun();
				run.setText("SSS_CHANGES_" + String.format("%05d", i));
				run.setFontSize(12);
				
			}
			
			FileOutputStream out = new FileOutputStream(new java.io.File("./CreateWordStyles.docx"));
			document.write(out);
			out.close();
			document.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
