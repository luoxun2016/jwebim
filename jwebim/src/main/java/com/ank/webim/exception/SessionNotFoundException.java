package com.ank.webim.exception;

public class SessionNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 9031784837335092505L;

	public SessionNotFoundException(String message) {
		super(message);
	}

	public SessionNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
