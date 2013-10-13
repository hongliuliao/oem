package org.github.oem.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MappingUtils {
	public static String path=ConfigUtils.rb.getString("propertyFilePath");
	public static Map configMap=new HashMap();
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void init() throws Exception{
		File file=new File(path);
		File[] files=file.listFiles();
		SAXReader saxReader = new SAXReader();
		
		for(int i=0;i<files.length;i++){
			if(files[i].getName().endsWith(".xml")){
				Document document = saxReader.read(files[i]);
				String className=document.getRootElement().element("class").attribute("name").getText();
				OemObject oem=getOemObjects(document);
				configMap.put(className, oem);	
			}
		}
	}
	/**
	 * 得到Oem对象
	 * @param document 每一个映射文件对应的文档对象
	 * @return Oem对象
	 */
	public static OemObject getOemObjects(Document document){
//		List oemList=new ArrayList();
		OemObject oem=new OemObject();
		String className=document.getRootElement().element("class").attribute("name").getText();
		oem.setClassName(className);
		//判断是否是行转列的情况
		Element element=(Element) document.getRootElement().element("class").elements().get(0);
		if(!"one-to-many".equals(element.getName())){
			List propertys=getPropertys(document.getRootElement().element("class"));
			oem.setPropertys(propertys);
		}else{
			List oneToManyObjects=new ArrayList();
			List oneToManyElemList=document.getRootElement().element("class").elements();
			for(int i=0;i<oneToManyElemList.size();i++){
				Element ele=(Element)oneToManyElemList.get(i);
				OneToManyObject oneToManyObject=getPropertysForOneToMany(ele);
				oneToManyObjects.add(oneToManyObject);
			}
			oem.setOneToManyObjects(oneToManyObjects);
		}
		return oem;
	}
	/**
	 * 得到属性对象,针对最简单的情况
	 * @param element class节点
	 * @return 属性对象集合
	 */
	public static List getPropertys(Element element){
		List elemList=element.elements();
		
		List propertyList=new ArrayList();
		for(int i=0;i<elemList.size();i++){
			Element elem=(Element) elemList.get(i);
			PropertyObject property=new PropertyObject();
			String name=elem.attribute("name").getText();
			String type=elem.attribute("type").getText();
			property.setName(name);
			property.setType(type);
			propertyList.add(property);
		}
		return propertyList;
	}
	/**
	 * 得到属性集合,针对行转列的情况
	 * @param element class节点
	 * @return 属性对象集合
	 */
	public static OneToManyObject getPropertysForOneToMany(Element element){
		List elemList=element.elements();
		OneToManyObject oneToManyObject=new OneToManyObject();
		List propertyList=new ArrayList();
		for(int i=0;i<elemList.size();i++){
			Element elem=(Element) elemList.get(i);
			PropertyObject property=new PropertyObject();
			String name=elem.attribute("name").getText();
			String type=elem.attribute("type").getText();
			int dataIndex=-1;//设置索引的默认值
			if(elem.attribute("dataIndex")!=null){
				dataIndex=Integer.parseInt(elem.attribute("dataIndex").getText());
			}
			
			String value=null;
			if(elem.attribute("value")!=null){
				value=elem.attribute("value").getText();
			}
			
			property.setName(name);
			property.setType(type);
			property.setDataIndex(dataIndex);
			property.setValue(value);
			propertyList.add(property);
		}
		oneToManyObject.setPropertys(propertyList);
		return oneToManyObject;
	}
}
