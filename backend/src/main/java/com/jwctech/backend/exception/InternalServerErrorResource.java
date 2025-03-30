package com.jwctech.backend.exception;

public class InternalServerErrorResource extends RuntimeException {
    public static final long serialVersionUID = 1;
    public InternalServerErrorResource(String message) {
        super(message);
    }
}