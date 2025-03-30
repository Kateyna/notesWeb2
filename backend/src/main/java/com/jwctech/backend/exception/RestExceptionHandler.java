package com.jwctech.backend.exception;

import com.jwctech.backend.entities.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoteNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorResource.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(InternalServerErrorResource ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Внутренняя ошибка сервера");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceConflict.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(ResourceConflict ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.CONFLICT.value());
        error.setMessage("Конфликт данных на сервере");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestResource.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(BadRequestResource ex) {
        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Ошибка Запроса");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}



