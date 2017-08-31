package org.github.oem.config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MappingUtils {
	public static Map configMap=new HashMap();
	
	public static void init(URL mappingConfigDir) {
		try {
			File file=new File(mappingConfigDir.getFile());
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
		} catch (Exception e) {
			throw new RuntimeException(e);
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
		List<PropertyObject> propertys=getPropertys(document.getRootElement().element("class"));
		oem.setPropertys(propertys);
		
		return oem;
	}
	/**
	 * 得到属性对象,针对最简单的情况
	 * @param element class节点
	 * @return 属性对象集合
	 */
	public static List<PropertyObject> getPropertys(Element element){
		List elemList=element.elements();
		
		List propertyList=new ArrayList();
		for(int i=0;i<elemList.size();i++){
			Element elem=(Element) elemList.get(i);
			PropertyObject property=new PropertyObject();
			String name=elem.attribute("name").getText();
			String type=elem.attribute("type").getText();
			Attribute colIndexAttribute = elem.attribute("colIndex");
			if(colIndexAttribute != null) {
				property.setColIndex(Integer.parseInt(colIndexAttribute.getText()));
			}
			
			property.setName(name);
			property.setType(type);
			propertyList.add(property);
		}
		return propertyList;
	}
	
}
