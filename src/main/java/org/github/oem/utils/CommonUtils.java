package org.github.oem.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.github.oem.config.MappingUtils;
import org.github.oem.config.OemObject;
import org.github.oem.config.PropertyObject;




public class CommonUtils {
	private static final Log log = LogFactory.getLog(CommonUtils.class);
	public static List constType=new ArrayList();
	static{
		constType.add(Long.class);
		constType.add(Integer.class);
		constType.add(Double.class);
		constType.add(Float.class);
		constType.add(java.util.Date.class);
		constType.add(java.sql.Date.class);
		constType.add(String.class);
		constType.add(Boolean.class);
		constType.add(Timestamp.class);
		constType.add(BigDecimal.class);
		constType.add(long.class);
		constType.add(int.class);
		constType.add(double.class);
		constType.add(boolean.class);
		constType.add(float.class);
	}

	public static void setProperty(Object obj,String name,Object value){
		try {
			BeanUtils.setProperty(obj, name, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public static OemObject buildOemByClass(Class clazz){
		OemObject oem=new OemObject();
		List propertyList=new ArrayList();
		Field[] fields=clazz.getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			PropertyObject property=new PropertyObject();
			property.setName(fields[i].getName());
			property.setType(getSimpleName(fields[i].getType().getName()));
			propertyList.add(property);
		}
		oem.setClassName(clazz.getName());
		oem.setPropertys(propertyList);
		return oem;
	}
	/**
	 * 得到多个小数点格式的字符串的最后一个
	 * @param name 字符串
	 * @return 截取后的字符串
	 */
	public static String getSimpleName(String name){
		if(name.lastIndexOf(".")!=-1){
			String temp=name.substring(name.lastIndexOf(".")+1);
			return temp;
		}else{
			return name;
		}
	}
	public static List getPropertys(Class clazz){
		OemObject oem= (OemObject) MappingUtils.configMap.get(clazz.getName());
		return oem.getPropertys();
	}
	public static Class getType(String typeName){
		if("String".equals(typeName)){
			return String.class;
		}else if("Integer".equals(typeName)||"int".equals(typeName)){
			return Integer.class;
		}else if("Date".equals(typeName)){
			return java.util.Date.class;
		}else if("Double".equals(typeName)||"double".equals(typeName)){
			return Double.class;
		}else if("Float".equals(typeName)||"float".equals(typeName)){
			return Float.class;
		}else if("BigDecimal".equals(typeName)){
			return BigDecimal.class;
		}else{
			log.error("不支持的类型:"+typeName);
			return null;
		}
	}
	public static Object getNewObject(Class clazz){
		try {
			Object obj=clazz.newInstance();
			Field[] fields=clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if(!constType.contains(fields[i].getType())){
					Object subObj=fields[i].getType().newInstance();
					CommonUtils.setProperty(obj, fields[i].getName(), subObj);
				}
			}
			return obj;
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	public static String getFirstUpName(String fieldName){
		fieldName=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		return fieldName;
	}
	public static String getFieldName(String tableField){
		String[] names=tableField.toLowerCase().split("_");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<names.length;i++){
			if(i!=0){
				names[i]=CommonUtils.getFirstUpName(names[i]);
			}
			sb.append(names[i]);
		}
		return sb.toString();
	}
}
