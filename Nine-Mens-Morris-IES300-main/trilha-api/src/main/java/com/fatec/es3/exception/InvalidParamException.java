package com.fatec.es3.exception;

public class InvalidParamException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	public InvalidParamException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
