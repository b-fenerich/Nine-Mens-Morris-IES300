package com.fatec.es3.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public NotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
