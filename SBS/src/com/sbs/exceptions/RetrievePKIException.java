package com.sbs.exceptions;

public class RetrievePKIException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RetrievePKIException(String  message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
