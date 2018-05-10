package com.znsd.pdh.exception;

public class JsonParseException extends MetaDataException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ERROR_MSG = "param is not a json type";
	
	public JsonParseException() {
		super(ERROR_MSG);
	}
	
	public JsonParseException(String msg) {
		super(msg);
	}
	
}
