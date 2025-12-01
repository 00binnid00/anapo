package com.example.anapo.user.application.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String createdAt;
    private String updatedAt;
}
