package com.jwctech.backend.dto;

public record NoteDto(
        Long id,
        String name,
        String description) {
}