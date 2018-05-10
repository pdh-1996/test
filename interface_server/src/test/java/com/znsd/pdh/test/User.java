package com.znsd.pdh.test;

import java.util.Set;

public class User {

	private String uuid;

	private String name;

	private String password;

	private Clazz clazz;

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

	private Set<Student> list;

	public Set<Student> getList() {
		return list;
	}

	public void setList(Set<Student> list) {
		this.list = list;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [uuid=" + uuid + ", name=" + name + ", password=" + password + ", clazz=" + clazz + ", list="
				+ list + "]";
	}

}
