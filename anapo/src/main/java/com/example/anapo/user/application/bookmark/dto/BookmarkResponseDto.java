package com.example.anapo.user.application.bookmark.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookmarkResponseDto {
    private Long id;
    private Long userId;
    private Long hospitalId;
    private String hosName;
    private String hosAddress;
}
