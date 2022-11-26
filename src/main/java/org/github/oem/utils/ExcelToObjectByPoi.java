package org.github.oem.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.github.oem.config.ConfigUtils;
import org.github.oem.config.MappingUtils;
import org.github.oem.config.OemObject;
import org.github.oem.config.PropertyObject;


public class ExcelToObjectByPoi  {
	
	public static int dataStartRow=ConfigUtils.dataStartRow;
	public static boolean isMultiSheet=ConfigUtils.isMultiSheet;
    private static Log log = LogFactory.getLog(ExcelToObjectByPoi.class);
	
	public ExcelToObjectByPoi(URL mappingConfigDir) {
		super();
		MappingUtils.init(mappingConfigDir);
	}

	/**
	 * 读取单个Sheet
	 * @param sheet 工作簿中的sheet
	 * @param clazz 要转换的类
	 * @return 对象集合
	 * @throws Exception 异常
	 */
	public <T> List<T> readSheet(Sheet sheet,Class<T> clazz){
		try {
			List<T> list=new ArrayList<T>();
			for(int i=dataStartRow;i<=sheet.getLastRowNum();i++){
				
				OemObject oem= (OemObject) MappingUtils.configMap.get(clazz.getName());
				if(oem==null){
					oem=CommonUtils.buildOemByClass(clazz);
				}
				Row row=sheet.getRow(i);
				T obj=clazz.newInstance();
				List<PropertyObject> propertys=oem.getPropertys();
				for(int j=0;j<propertys.size();j++){
					PropertyObject property=(PropertyObject) propertys.get(j);
					Cell cell = null;
                    short colIdx = (short) j;
					if(property.getColIndex() != -1) {
                        colIdx = (short) property.getColIndex();
					} 
                    cell = row.getCell(colIdx);
                    if (cell == null) {
                        log.warn("get cell is null, col idx:" + colIdx + ", j:" + j + ", i:" + i);
                        continue;
                    }
					Object value=PoiUtils.getCellValue(cell, CommonUtils.getType(property.getType()));
					CommonUtils.setProperty(obj, property.getName(), value);
				}
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 将工作簿中的记录转换成对象集合的数组,根据配置来确定数组中的个数,如果isMultiSheet为false
	 * 的话将只返回一个list的list数组,而为true的时候根据sheet的个数来返回
	 * @param workbook 要转换为对象的工作簿
	 * @param clazz 要转换的类
	 * @return 数组集合
	 * @throws Exception 异常
	 */
	public <T> List<T>[] readWorkbook(Workbook workbook,Class<T>[] clazzs) throws Exception{
		if(isMultiSheet){
			List<T>[] lists=new List[workbook.getNumberOfSheets()];
			for(int i=0;i<lists.length;i++){
				Sheet sheet=workbook.getSheetAt(i);
				lists[i]=this.readSheet(sheet, clazzs[i]);
			}
			return lists;
		}else{
			List<T>[] lists=new List[1];
			lists[0]=this.readSheet(workbook.getSheetAt(0), clazzs[0]);
			return lists;
		}
	}
	/**
	 * 读取sheet,将之转换为指定对象集合,该方法主要针对一个类对应多张表的时候使用
	 * @param sheet 工作簿中的sheet
	 * @param clazz 指定的类
	 * @param className 配置文件中的类名
	 * @return 对象集合
	 * @throws Exception 异常
	 */
	public List readSheet(HSSFSheet sheet,Class clazz,String className) throws Exception{
		List list=new ArrayList();
		for(int i=dataStartRow;i<=sheet.getLastRowNum();i++){
			OemObject oem= (OemObject) MappingUtils.configMap.get(className);
			HSSFRow row=sheet.getRow(i);
			Object obj=CommonUtils.getNewObject(clazz);
			List propertys=oem.getPropertys();
			for(int j=0;j<propertys.size();j++){
				PropertyObject property=(PropertyObject) propertys.get(j);
				HSSFCell cell=row.getCell((short) j);
				Object value=PoiUtils.getCellValue(cell, CommonUtils.getType(property.getType()));
				CommonUtils.setProperty(obj, property.getName(), value);
			}
			list.add(obj);
		}
		return list;
	}
	
}
