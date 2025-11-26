package com.example.anapo.user.application.bookmark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkCreateDto {
    private Long userId;      // Account FK
    private Long hospitalId;  // Hospital FK
}
