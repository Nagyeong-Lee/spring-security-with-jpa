package org.zerok.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerok.club.dto.NoteDTO;
import org.zerok.club.entity.Note;
import org.zerok.club.repository.NoteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public Long register(NoteDTO noteDTO) {
        Note result = noteRepository.save(dtoToEntity(noteDTO));
        return result.getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        return noteRepository.findById(num)
                .map(this::entityToDTO)
                .orElseThrow(() -> new RuntimeException("note is not exist"));
    }

    @Override
    public void modify(NoteDTO noteDTO) {
        Note result = noteRepository.findById(noteDTO.getNum())
                .orElseThrow(() -> new RuntimeException("note is not exist"));

        result.changeTitle(noteDTO.getTitle());
        result.changeContent(noteDTO.getContent());

        noteRepository.save(result);
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> findAllByWriter(String writerEmail) {
        return noteRepository.findByWriterEmail(writerEmail)
                .stream().map(this::entityToDTO)
                .collect(Collectors.toList());
    }
}
