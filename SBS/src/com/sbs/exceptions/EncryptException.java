package com.sbs.exceptions;

public class EncryptException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EncryptException(String  message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
