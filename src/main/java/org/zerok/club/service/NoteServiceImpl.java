package org.zerok.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerok.club.dto.NoteDTO;
import org.zerok.club.entity.Note;
import org.zerok.club.repository.NoteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {
        Note note = noteRepository.save(dtoToEntity(noteDTO));
        return note.getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> result = noteRepository.findById(num);
        if (result.isPresent()) {
            return entityToDTO(result.get());
        }
        return null;
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Long num = noteDTO.getNum();
        Optional<Note> result = noteRepository.findById(num);
        if (result.isPresent()) {
            Note note = result.get();
            note.changeTitle(noteDTO.getTitle());
            note.changeContent(noteDTO.getContent());
            noteRepository.save(dtoToEntity(noteDTO));
        }
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        List<Note> noteList = noteRepository.findAllByEmail(writerEmail);
        return noteList.stream()
                .map(note -> entityToDTO(note))
                .collect(Collectors.toList());
    }
}
