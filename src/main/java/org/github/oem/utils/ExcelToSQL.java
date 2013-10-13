/**
 * 
 */
package org.github.oem.utils;

import java.io.File;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <pre>
 * Title:由excel生成sql的工具类
 * Description:将excel中的数据生成相应的insert的SQL语句
 * </pre>
 * @author liaohongliu liaohl@yuchengtech.com
 * @version 1.0   Jul 23, 2009
 * 
 * <pre>
 * 修改记录
 * 	  修改后版本:      修改人:     修改时间:       修改内容
 * </pre>
 */
public class ExcelToSQL {
	private  static final Log log = LogFactory.getLog(ExcelToSQL.class);
	/**
	 * 生成插入的sql语句,第一行不会被当数据插入
	 * @param filePath excel文件的路径
	 * @param modelString 模板字符串,可以查考我写的例子
	 */
	public static void createInsertPoi(String filePath,String modelStr){
		InputStream stream;
		try {
			stream = new java.io.FileInputStream(filePath);
			HSSFWorkbook workbook = new HSSFWorkbook(stream);
			HSSFSheet sheet=workbook.getSheetAt(0);
			for(int i=sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
				HSSFRow row=sheet.getRow(i);
				//StringBuffer sb=new StringBuffer("insert into bps_o_paramgr_adj_para values(");
				String tempStr=modelStr;
				for(int j=row.getFirstCellNum()+1;j<row.getLastCellNum()+1;j++){
					HSSFCell cell=row.getCell((short) (j-1));
					String temp=PoiUtils.getCellStringValue(cell);
					tempStr=tempStr.replaceAll("p"+j, temp);
				}
				System.out.println(tempStr);
			}
			stream.close();
		} catch (Exception e) {
			log.error(e);
		}
	}
	public static void createInsertJxl(String filePath,String modelStr){
		try {
			Workbook workbook=Workbook.getWorkbook(new File(filePath));
			Sheet sheet=workbook.getSheet(0);
			for(int i=0;i<sheet.getRows();i++){
				String tempStr=modelStr;
				for(int j=1;j<=sheet.getColumns();j++){
					Cell cell=sheet.getCell(j-1, i);
					tempStr=tempStr.replaceAll("p"+j, JxlUtils.getCellStringValue(cell));
				}
				System.out.println(tempStr);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	public static void main(String[] args) {
//		createInsertJxl("d:/User.xls", "insert into tb_user values('p1',p2,to_date('p3','yyyy-mm-dd'),p4);");
//		createInsertJxl("d:/task_no.xls", "insert into task_no_table values('p1');");
		createInsertJxl("d:/news.xls", "insert into EDUINFO_NEWS values(p1,'p2','p3','p4','p5','','','');");
//		createInsertJxl("d:/User.xls", "'p1'|p2|'p3'|p4");
	}
}
