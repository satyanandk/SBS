package com.sbs.exceptions;

public class DecryptException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DecryptException(String  message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
