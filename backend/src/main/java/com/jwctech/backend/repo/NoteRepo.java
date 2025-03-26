package com.jwctech.backend.repo;

import com.jwctech.backend.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo  extends JpaRepository<Note,Long> {
}
