package com.example.anapo.user.application.notice.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NoticeResponseDto { // 응답용
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long hospitalId;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}