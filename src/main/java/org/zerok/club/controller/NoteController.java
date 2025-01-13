package org.zerok.club.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerok.club.dto.NoteDTO;
import org.zerok.club.service.NoteService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    @PostMapping
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {
      Long num = noteService.register(noteDTO);
      log.info(" ### num : {} ### ", num);
      return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping("/{num}")
    public ResponseEntity<NoteDTO> get(@PathVariable Long num) {
        NoteDTO result = noteService.get(num);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all/{email}")
    public ResponseEntity<List<NoteDTO>> getNotesByEmail(@PathVariable String email) {
        List<NoteDTO> result = noteService.findAllByWriter(email);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<String> remove(@PathVariable Long num) {
        noteService.remove(num);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO) {
        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }
}
