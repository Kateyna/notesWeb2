package com.jwctech.backend.Repo;

import com.jwctech.backend.Entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo  extends JpaRepository<Note,Long> {
    List<Note> findByName(String name);



}
