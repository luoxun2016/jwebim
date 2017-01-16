package com.ank.webim.exception;

public class MessageDecodeException extends RuntimeException {

	private static final long serialVersionUID = -5063361278237270461L;

	public MessageDecodeException(String message) {
		super(message);
	}

	public MessageDecodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
