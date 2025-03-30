package com.jwctech.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public static final long serialVersionUID = 1;
    public ResourceNotFoundException(String message) {
        super(message);
    }
}