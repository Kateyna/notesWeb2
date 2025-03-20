package com.jwctech.backend.Services;

import com.jwctech.backend.Entities.Note;
import com.jwctech.backend.Repo.NoteRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepo noteRepo;

    @CrossOrigin
    @GetMapping("/note")
    @Transactional
    public ResponseEntity<List<Note>> getAllNotes(@RequestParam(required = false) String name) {
        try {
            List<Note> notes = new ArrayList<Note>();

            if (name == null)
                noteRepo.findAll().forEach(notes::add);
            else
                noteRepo.findByName(name).forEach(notes::add);

            if (notes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(notes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/note/{id}")
    @Transactional
    public ResponseEntity<Note> getNoteById(@PathVariable("id") long id) {
        Optional<Note> noteData = noteRepo.findById(id);

        if (noteData.isPresent()) {
            return new ResponseEntity<>(noteData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/note")
    @Transactional
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        try {
            Note _note = noteRepo
                    .save(new Note(note.getName(), note.getDescription()));
            return new ResponseEntity<>(_note, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/note/{id}")
    @Transactional
    public ResponseEntity<Note> updateNote(@PathVariable("id") long id, @RequestBody Note note) {
        Optional<Note> tutorialData = noteRepo.findById(id);

        if (tutorialData.isPresent()) {
            Note _note = tutorialData.get();
            _note.setName(note.getName());
            _note.setDescription(note.getDescription());
            return new ResponseEntity<>(noteRepo.save(_note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/note/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteNote(@PathVariable("id") long id) {
        try {
            noteRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/note")
    @Transactional
    public ResponseEntity<HttpStatus> deleteAllNotes() {
        try {
            noteRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
