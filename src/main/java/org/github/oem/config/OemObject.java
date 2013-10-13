package org.github.oem.config;

import java.util.List;

public class OemObject {
	public String className;
	public List propertys;
	private List oneToManyObjects;

	public List getOneToManyObjects() {
		return oneToManyObjects;
	}
	public void setOneToManyObjects(List oneToManyObjects) {
		this.oneToManyObjects = oneToManyObjects;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List getPropertys() {
		return propertys;
	}
	public void setPropertys(List propertys) {
		this.propertys = propertys;
	}
	
}
