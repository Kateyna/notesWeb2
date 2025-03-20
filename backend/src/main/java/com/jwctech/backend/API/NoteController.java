package com.jwctech.backend.API;

import com.jwctech.backend.Entities.Note;
import com.jwctech.backend.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @CrossOrigin
    @GetMapping("/note")
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) String name) {
        return noteService.getAllNotes(name);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") long id) {
        return noteService.getNoteById(id);
    }

    @PostMapping("/note")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") long id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") long id) {
        return noteService.deleteNote(id);
    }

//    @DeleteMapping("/note")
//    public ResponseEntity<HttpStatus> deleteAllNotes() {
//        return noteService.deleteAllNotes();
//    }
}