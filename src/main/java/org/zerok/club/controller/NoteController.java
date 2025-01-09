package org.zerok.club.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.zerok.club.common.ApiResponse;
import org.zerok.club.dto.NoteDTO;
import org.zerok.club.service.NoteService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody NoteDTO noteDTO) {
        log.info("noteDTO : {}", noteDTO);
        Long num = noteService.register(noteDTO);
        return ApiResponse.createSuccess(num);
    }

    @GetMapping("/{num}")
    public ApiResponse read(@PathVariable Long num) {
        NoteDTO result = noteService.get(num);
        return ApiResponse.createSuccess(result);
    }

    @GetMapping("/all")
    public ApiResponse getAllNotesByWriterEmail(@RequestParam String email) {
        List<NoteDTO> result = noteService.getAllWithWriter(email);
        return ApiResponse.createSuccess(result);
    }

    @DeleteMapping("/{num}")
    public ApiResponse deleteById(@PathVariable Long num) {
        noteService.remove(num);
        return ApiResponse.createSuccessWithNoContent();
    }

    @PutMapping("/{num}")
    public ApiResponse updateById(@RequestBody NoteDTO noteDTO) {
        noteService.modify(noteDTO);
        return ApiResponse.createSuccessWithNoContent();
    }
}
