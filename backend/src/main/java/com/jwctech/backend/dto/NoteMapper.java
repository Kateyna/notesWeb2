package com.jwctech.backend.dto;

import com.jwctech.backend.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {

    @Mapping(target = "id", ignore = true)
    Note toEntity(NoteDto dto);

    NoteDto toNoteDto(Note entity);

    List<NoteDto> toNoteDtoList(List<Note> entities);

    @Mapping(target = "id", ignore = true)
    void updateNoteFromDto(NoteDto dto, @MappingTarget Note entity);
}