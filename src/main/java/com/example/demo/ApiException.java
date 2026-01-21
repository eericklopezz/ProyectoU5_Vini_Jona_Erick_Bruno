package com.example.demo;

public class ApiException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	private final int code;

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}