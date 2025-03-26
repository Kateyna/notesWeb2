package com.jwctech.backend.services;

import com.jwctech.backend.dto.NoteDto;
import com.jwctech.backend.dto.NoteMapper;
import com.jwctech.backend.entities.Note;

import com.jwctech.backend.exeption.ResourceNotFoundException;
import com.jwctech.backend.repo.NoteRepo;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Slf4j
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
    public List<NoteDto> getAllNotes() {
        log.info("Запрос всех заметок");
        List<Note> notes = noteRepo.findAll();
        if (notes.isEmpty()) {
            log.warn("Список заметок пуст");
            throw new ResourceNotFoundException("В базе данных не найдено ни одной заметки");
        }
        log.debug("Найдено {} заметок", notes.size());
        return noteMapper.toNoteDtoList(notes);
    }

    @Transactional(readOnly = true)
    public NoteDto getNoteById(long id) {
        log.info("Поиск заметки по id: {}", id);
        return noteRepo.findById(id)
                .map(note -> {
                    log.debug("Заметка найдена: {}", id);
                    return noteMapper.toNoteDto(note);
                })
                .orElseThrow(() -> {
                    log.error("Заметка с id {} не найдена", id);
                    return new ResourceNotFoundException("Заметка не найдена с id: " + id);
                });
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class,
            noRollbackFor = {IllegalArgumentException.class})
    public NoteDto createNote(@Valid NoteDto noteDto) {
        log.info("Создание новой заметки: {}", noteDto);
        try {
            Note note = noteMapper.toEntity(noteDto);
            Note savedNote = noteRepo.save(note);
            log.info("Заметка успешно создана с id: {}", savedNote.getId());
            return noteMapper.toNoteDto(savedNote);
        } catch (DataIntegrityViolationException ex) {
            log.error("Ошибка создания заметки: {}", ex.getMessage());
            throw new ResourceNotFoundException("Ошибка создания заметки: нарушение ограничений БД");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class},
            noRollbackFor = {IllegalArgumentException.class})
    public NoteDto updateNote(long id, @Valid NoteDto noteDto) {
        log.info("Обновление заметки с id: {}", id);
        Note existingNote = noteRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Заметка для обновления с id {} не найдена", id);
                    return new ResourceNotFoundException("Заметка для обновления не найдена с id: " + id);
                });
        noteMapper.updateNoteFromDto(noteDto, existingNote);
        Note updatedNote = noteRepo.save(existingNote);
        log.debug("Заметка обновлена: {}", noteDto);
        return noteMapper.toNoteDto(updatedNote);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteNote(long id) {
        log.info("Удаление заметки с id: {}", id);
        Note note = noteRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Заметка для удаления с id {} не найдена", id);
                    return new ResourceNotFoundException("Заметка для удаления не найдена с id: " + id);
                });
        noteRepo.delete(note);
        log.info("Заметка с id {} успешно удалена", id);
    }
}