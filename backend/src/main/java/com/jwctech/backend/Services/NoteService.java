package com.jwctech.backend.Services;

import com.jwctech.backend.DTO.NoteDto;
import com.jwctech.backend.DTO.NoteMapper;
import com.jwctech.backend.Entities.Note;

import com.jwctech.backend.Repo.NoteRepo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService  {

    private final NoteRepo noteRepo;
    private final NoteMapper noteMapper;

    @Autowired
    public NoteService(NoteRepo noteRepo, NoteMapper noteMapper) {
        this.noteRepo = noteRepo;
        this.noteMapper = noteMapper;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<NoteDto>> getAllNotes(String name) {
        try {
            List<Note> notes = new ArrayList<>();

            if (name == null) noteRepo.findAll().forEach(notes::add);
            else noteRepo.findByName(name).forEach(notes::add);

            if (notes.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(noteMapper.toNoteDtoList(notes), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Обновите метод getNoteById
    @Transactional(readOnly = true)
    public ResponseEntity<NoteDto> getNoteById(long id) {
        Optional<Note> noteData = noteRepo.findById(id);
        return noteData.map(note ->
                        new ResponseEntity<>(noteMapper.toNoteDto(note), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class,
            noRollbackFor = {IllegalArgumentException.class})
    public ResponseEntity<NoteDto> createNote(@Valid NoteDto noteDto) {
        try {
            Note note = noteMapper.toEntity(noteDto);
            Note savedNote = noteRepo.save(note);
            return new ResponseEntity<>(noteMapper.toNoteDto(savedNote), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class})
    public ResponseEntity<NoteDto> updateNote(long id, @Valid NoteDto noteDto) {
        return noteRepo.findById(id)
                .map(existingNote -> {
                    noteMapper.updateNoteFromDto(noteDto, existingNote);
                    Note updatedNote = noteRepo.save(existingNote);
                    return new ResponseEntity<>(noteMapper.toNoteDto(updatedNote), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> deleteNote(long id) {
        return noteRepo.findById(id)
                .map(note -> {
                    noteRepo.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @Transactional
//    public ResponseEntity<HttpStatus> deleteAllNotes() {
//        try {
//            noteRepo.deleteAll();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}