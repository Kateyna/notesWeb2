package com.jwctech.backend.Services;

import com.jwctech.backend.Entities.Note;

import com.jwctech.backend.Repo.NoteRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepo noteRepo;

    @Transactional
    public ResponseEntity<List<Note>> getAllNotes(String name) {
        try {
            List<Note> notes = new ArrayList<>();

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

    @Transactional
    public ResponseEntity<Note> getNoteById(long id) {
        Optional<Note> noteData = noteRepo.findById(id);

        if (noteData.isPresent()) {
            return new ResponseEntity<>(noteData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<Note> createNote(Note note) {
        try {
            Note _note = noteRepo.save(new Note(note.getName(), note.getDescription()));
            return new ResponseEntity<>(_note, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Note> updateNote(long id, Note note) {
        Optional<Note> noteData = noteRepo.findById(id);

        if (noteData.isPresent()) {
            Note _note = noteData.get();
            _note.setName(note.getName());
            _note.setDescription(note.getDescription());
            return new ResponseEntity<>(noteRepo.save(_note), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteNote(long id) {
        try {
            noteRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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