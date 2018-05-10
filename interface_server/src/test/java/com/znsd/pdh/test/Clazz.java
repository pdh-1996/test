package com.znsd.pdh.test;

public class Clazz {

	private Object objName;

	private String uuid;
	
	private Integer id;

	private String name;

	private Grade grade;

	public Object getObjName() {
		return objName;
	}

	public void setObjName(Object objName) {
		this.objName = objName;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "Clazz [uuid=" + uuid + ", id=" + id + ", name=" + name + ", grade=" + grade + "]";
	}

}
