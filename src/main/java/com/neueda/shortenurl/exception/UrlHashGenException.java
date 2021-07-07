package com.neueda.shortenurl.exception;

public class UrlHashGenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UrlHashGenException(String message) {
		super(message);
	}
	
	public UrlHashGenException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
