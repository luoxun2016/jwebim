package com.ank.webim.exception;

public class UnsupportedHttpRequestMethodException extends RuntimeException{
	
	private static final long serialVersionUID = -9048413817381653332L;

	public UnsupportedHttpRequestMethodException(String message) {
		super(message);
	}

	public UnsupportedHttpRequestMethodException(String message, Throwable cause) {
		super(message, cause);
	}
}
