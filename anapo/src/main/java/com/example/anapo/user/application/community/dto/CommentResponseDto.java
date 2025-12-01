package com.example.anapo.user.application.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentResponseDto {

    private Long id;
    private Long communityId;
    private String content;
    private String writer;
    private String createdAt;
    private String updatedAt;

    private List<CommentResponseDto> children; // 대댓글 트리 구조로 하기 위해서
}
