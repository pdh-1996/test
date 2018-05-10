package com.znsd.pdh.test;

public class Student {

	private String uuid ;
	
	private String name ;
	
	private String type ;

	private Object objName;
	public Object getObjName() {
		return objName;
	}

	public void setObjName(Object objName) {
		this.objName = objName;
	}
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Student [uuid=" + uuid + ", name=" + name + ", type=" + type + "]";
	}
	
	
}
