package com.example.anapo.user.application.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
    private String writer;
    private Long accId;
    private Long parentId;
}
