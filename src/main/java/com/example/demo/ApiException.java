package com.example.demo;

public class ApiException extends RuntimeException {

	private String code;

	public ApiException(String message) {
		super(message);
		this.code = "ERROR";
	}

	public ApiException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}