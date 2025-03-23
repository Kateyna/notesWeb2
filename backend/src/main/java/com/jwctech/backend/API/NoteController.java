package com.jwctech.backend.API;

import com.jwctech.backend.DTO.NoteDto;
import com.jwctech.backend.DTO.NoteMapper;
import com.jwctech.backend.Entities.Note;
import com.jwctech.backend.Repo.NoteRepo;
import com.jwctech.backend.Services.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    @Autowired
    private NoteService noteService;

    @CrossOrigin
    @GetMapping("/note")
    public ResponseEntity<List<NoteDto>>  getAllNotes(@RequestParam(required = false) String name) {
        return noteService.getAllNotes(name);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("id") long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping("/note")
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<NoteDto> updateNote(
            @PathVariable("id") long id,
            @Valid @RequestBody NoteDto noteDto
    ) {
        return noteService.updateNote(id, noteDto);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") long id) {
        return noteService.deleteNote(id);
    }


//    @DeleteMapping("/note")
//    public ResponseEntity<HttpStatus> deleteAllNotes() {
//        return noteService.deleteAllNotes();
//    }
}