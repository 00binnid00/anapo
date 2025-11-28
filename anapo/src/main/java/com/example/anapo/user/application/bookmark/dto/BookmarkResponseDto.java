package com.example.anapo.user.application.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookmarkResponseDto {
    private Long id;
    private Long userId;
    private Long hospitalId;
    private String hosName;
    private String hosAddress;
    private LocalDateTime createdAt;  // 추가됨
}
