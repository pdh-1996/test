package com.znsd.pdh.exception;

public class ObjectNameNotFoundException extends MetaDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ERROR_MSG = "this object don't have a type name"
			+ " . if you want use this server . you must set a objName in this object";
	
	public ObjectNameNotFoundException() {
		super(ERROR_MSG);
	}
	
	public ObjectNameNotFoundException(String msg) {
		super(msg);
	}
	
}
