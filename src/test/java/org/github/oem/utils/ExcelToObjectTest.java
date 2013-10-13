package org.github.oem.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.github.oem.pojo.User;
import org.junit.Test;

public class ExcelToObjectTest {
	
	ExcelToObjectByPoi reader=new ExcelToObjectByPoi(ExcelToObjectTest.class.getResource("/"));  

	@Test
	public void testReadExcel() throws InvalidFormatException, IOException {
		File excelFile = new File(ExcelToObjectTest.class.getResource("/test.xlsx").getFile());
		Workbook workbook = WorkbookFactory.create(excelFile);
		List<User> users = reader.readSheet(workbook.getSheetAt(0), User.class);
		System.out.println(users);
	}

}
