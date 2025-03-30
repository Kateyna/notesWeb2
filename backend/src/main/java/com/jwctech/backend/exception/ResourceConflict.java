package com.jwctech.backend.exception;

public class ResourceConflict extends RuntimeException {
    public static final long serialVersionUID = 1;
    public ResourceConflict(String message) {
        super(message);
    }
}