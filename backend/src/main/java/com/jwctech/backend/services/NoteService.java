package com.jwctech.backend.services;

import com.jwctech.backend.dto.NoteDto;
import com.jwctech.backend.dto.NoteMapper;
import com.jwctech.backend.entities.Note;

import com.jwctech.backend.exeption.ResourceNotFoundException;
import com.jwctech.backend.repo.NoteRepo;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<NoteDto> getAllNotes(String name) {
        List<Note> notes = (name == null)
                ? noteRepo.findAll()
                : noteRepo.findByName(name);
        if (notes.isEmpty()) {
            throw new ResourceNotFoundException("No notes found in database");
        }
        return noteMapper.toNoteDtoList(notes);
    }

    @Transactional(readOnly = true)
    public NoteDto getNoteById(long id) {
        return noteRepo.findById(id)
                .map(noteMapper::toNoteDto)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class,
            noRollbackFor = {IllegalArgumentException.class})
    public NoteDto createNote(@Valid NoteDto noteDto) {
        try {
            Note note = noteMapper.toEntity(noteDto);
            Note savedNote = noteRepo.save(note);
            return noteMapper.toNoteDto(savedNote);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceNotFoundException("Cannot create note: invalid data");
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Unexpected error during note creation");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class})
    public NoteDto updateNote(long id, @Valid NoteDto noteDto) {
        Note existingNote = noteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        noteMapper.updateNoteFromDto(noteDto, existingNote);
        Note updatedNote = noteRepo.save(existingNote);
        return noteMapper.toNoteDto(updatedNote);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteNote(long id) {
        Note note = noteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        noteRepo.delete(note);
    }
}