package org.github.oem.utils;

import java.util.List;

public interface ExcelToObject {
	public List[] readExcel(String filePath,Class[] classes);
}
