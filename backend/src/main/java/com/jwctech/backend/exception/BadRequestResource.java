package com.jwctech.backend.exception;

public class BadRequestResource extends RuntimeException {
    public static final long serialVersionUID = 1;
    public BadRequestResource(String message) {
        super(message);
    }
}