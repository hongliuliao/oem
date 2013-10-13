package org.github.oem.utils;

import java.io.File;
import java.util.List;

import org.github.oem.pojo.User;
import org.junit.Test;

public class ExcelToObjectTest {
	
	ExcelToObject reader=new ExcelToObjectByPoi();  

	@Test
	public void testReadExcel() {
		String filePath = new File(ExcelToObjectTest.class.getResource("/test.xlsx").getFile()).getPath();
		List<User>[] users = reader.readExcel(filePath, new Class[] {User.class});
		User user = users[0].get(0);
		System.out.println(user);
	}

}
