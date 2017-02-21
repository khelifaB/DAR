package com.dar.exception;

@SuppressWarnings("serial")
public class DarException extends Exception{

	public DarException(String message) {
		super(message);
	}

	public DarException() {
		super();
	}

	public DarException(String message, Throwable cause) {
		super(message, cause);
	}

	public DarException(Throwable cause) {
		super(cause);
	}
	
	
	
	

}
