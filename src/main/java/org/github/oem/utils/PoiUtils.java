package org.github.oem.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

public class PoiUtils {
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
	public static Object getCellValue(Cell cell,Class type){
		if(type==String.class){
			return cell.getRichStringCellValue().getString();
		}else if(type==Integer.class){
			Double value=new Double(cell.getNumericCellValue());
			return new Integer(value.intValue());
		}else if(type==java.util.Date.class){
			return cell.getDateCellValue();
		}else if(type==Double.class){
			return new Double(cell.getNumericCellValue());
		}else{
			return null;
		}
	}
	public static String getCellStringValue(HSSFCell cell){
		if(cell==null||cell.toString().trim().equals("")){
			return "";
		}
		switch(cell.getCellType()){
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				return dateFormat.format(cell.getDateCellValue());
			}else{
				String value=new BigDecimal(new Double(cell.getNumericCellValue()).toString()).toString();
				return value;
			}
		default:
			return "";
		}
	}
	public static String double2Int(String doubleString){
		return new BigDecimal(new Double(doubleString).intValue()).toString();
	}
}
