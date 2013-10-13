package org.github.oem.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.github.oem.config.ConfigUtils;
import org.github.oem.config.MappingUtils;
import org.github.oem.config.OemObject;
import org.github.oem.config.OneToManyObject;
import org.github.oem.config.PropertyObject;



import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelToObjectByJxl implements ExcelToObject{
	public List[] readExcel(String filePath,Class[] classes) {
		try {
			Workbook workbook=Workbook.getWorkbook(new File(filePath));
			if(ConfigUtils.isMultiSheet==true){
				List[] lists=new List[workbook.getNumberOfSheets()];
				for(int i=0;i<workbook.getNumberOfSheets();i++){
					Sheet sheet=workbook.getSheet(i);
					lists[i]=readSheet(sheet, classes[i]);
				}
				return lists;
			}else{
				List[] lists=new List[1];
				Sheet sheet=workbook.getSheet(0);
				lists[0]=readSheet(sheet, classes[0]);
				return lists;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public List readSheet(Sheet sheet,Class clazz) throws Exception{
		List list=new ArrayList();
		OemObject oem= (OemObject) MappingUtils.configMap.get(clazz.getName());
		if(oem==null){
			oem=CommonUtils.buildOemByClass(clazz);
		}
		if(oem.getOneToManyObjects()==null){
			for(int i=ConfigUtils.dataStartRow;i<sheet.getRows();i++){
				Object obj=CommonUtils.getNewObject(clazz);
				for(int j=0;j<oem.getPropertys().size();j++){
					PropertyObject property=(PropertyObject) oem.getPropertys().get(j);
					Class type=CommonUtils.getType(property.getType());
					Object value=JxlUtils.getCellValue(sheet.getCell(j, i), type);
					CommonUtils.setProperty(obj, property.getName(), value);
				}
				list.add(obj);
			}
		}else{
			for(int i=ConfigUtils.dataStartRow;i<sheet.getRows();i++){
				List oneToManyList=oem.getOneToManyObjects();
				for(int j=0;j<oneToManyList.size();j++){
					Object obj=CommonUtils.getNewObject(clazz);
					OneToManyObject oneToManyObject=(OneToManyObject) oneToManyList.get(j);
					for(int k=0;k<oneToManyObject.getPropertys().size();k++){
						PropertyObject property=(PropertyObject) oneToManyObject.getPropertys().get(k);
						if(property.getDataIndex()!=-1){
							Cell cell=sheet.getCell((short) property.getDataIndex(),i);
							Object value=JxlUtils.getCellValue(cell, CommonUtils.getType(property.getType()));
							CommonUtils.setProperty(obj, property.getName(), value);
						}else{
							Object value=property.getValue();
							CommonUtils.setProperty(obj, property.getName(), value);
						}
					}
					list.add(obj);
				}
			}
		}
		return list;
	}
}
