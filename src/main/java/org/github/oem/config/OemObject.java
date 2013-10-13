package org.github.oem.config;

import java.util.List;

public class OemObject {
	public String className;
	public List<PropertyObject> propertys;

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<PropertyObject> getPropertys() {
		return propertys;
	}
	public void setPropertys(List<PropertyObject> propertys) {
		this.propertys = propertys;
	}
	
}
