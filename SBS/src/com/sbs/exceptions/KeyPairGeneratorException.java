package com.sbs.exceptions;

public class KeyPairGeneratorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public KeyPairGeneratorException(String  message) {
		super(message);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
}
