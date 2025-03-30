package com.jwctech.backend.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


import java.time.LocalDateTime;
@Data
@NoArgsConstructor // Добавьте если отсутствует
@AllArgsConstructor // Опционально
public class ErrorResponse {
    private int statusCode;
    private String message;
}