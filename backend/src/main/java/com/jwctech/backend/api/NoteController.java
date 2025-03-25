package com.jwctech.backend.api;

import com.jwctech.backend.dto.NoteDto;
import com.jwctech.backend.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private NoteService noteService;

    @CrossOrigin
    @GetMapping("/note")
    @Operation(summary = "Получить все заметки", description = "Возвращает список всех заметок")
    public ResponseEntity<List<NoteDto>>  getAllNotes(@RequestParam(required = false) String name) {
        return noteService.getAllNotes(name);
    }

    @GetMapping("/note/{id}")
    @Operation(summary = "Получить заметку по id", description = "Возвращает детали конкретной заметки по её id")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping("/note")
    @Operation(summary = "Создать новую заметку", description = "Возвращает заметку с указанным id")
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @PutMapping("/note/{id}")
    @Operation(summary = "Обновить существующую заметку", description = "Обновляет все поля заметки по её id")
    public ResponseEntity<NoteDto> updateNote(
            @PathVariable("id") long id,
            @Valid @RequestBody NoteDto noteDto
    ) {
        return noteService.updateNote(id, noteDto);
    }

    @DeleteMapping("/note/{id}")
    @Operation(summary = "Удалить заметку", description = "Удаляет заметку по указанному id")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") long id) {
        return noteService.deleteNote(id);
    }
}