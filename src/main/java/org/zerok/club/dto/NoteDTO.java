package org.zerok.club.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteDTO {

    private Long num;
    private String title;
    private String content;
    private String writerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
