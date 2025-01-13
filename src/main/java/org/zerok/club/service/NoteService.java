package org.zerok.club.service;

import org.zerok.club.dto.NoteDTO;
import org.zerok.club.entity.ClubMember;
import org.zerok.club.entity.Note;

import java.util.List;

public interface NoteService {
    Long register(NoteDTO noteDTO);

    NoteDTO get(Long num);

    void modify(NoteDTO noteDTO);

    void remove(Long num);

    List<NoteDTO> findAllByWriter(String writerEmail);

    default Note dtoToEntity(NoteDTO noteDTO) {
        return Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getContent())
                .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build())
                .build();
    }

    default NoteDTO entityToDTO(Note note) {
        return NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .createdAt(note.getCreatedAt())
                .modifiedAt(note.getUpdatedAt())
                .build();
    }
}
