/**
 * 
 */
package org.github.oem.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author liaohongliu
 *
 * 创建日期:2013-10-13 上午11:44:09
 */
public class User {

	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
