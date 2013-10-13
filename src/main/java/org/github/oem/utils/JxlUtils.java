/**
 * 
 */
package org.github.oem.utils;

import java.text.SimpleDateFormat;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;


/**
 * @author user
 *
 */
public class JxlUtils {
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");
	public static Object getCellValue(Cell cell,Class type){
		if(type==String.class){
			return cell.getContents();
		}else if(type==Integer.class){
			Double value=new Double(cell.getContents());
			return new Integer(value.intValue());
		}else if(type==java.util.Date.class){
			DateCell dateCell=(DateCell) cell;
			return dateCell.getDate();
		}else if(type==Double.class){
			return new Double(cell.getContents());
		}else{
			return null;
		}
	}

	public static String getCellStringValue(Cell cell){
		if(cell.getType()==CellType.DATE){
			DateCell dateCell=(DateCell) cell;
			return dateFormat.format(dateCell.getDate());
		}
		return cell.getContents();
	}
}
