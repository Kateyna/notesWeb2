package com.jwctech.backend.DTO;

import com.jwctech.backend.Entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {

    NoteResponce toUserResponse(Note note); //map User to UserResponse

    List<NoteResponce> toUserResponseList(List<Note> note); //map list of User to list of UserResponse
}

