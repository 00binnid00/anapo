package com.example.anapo.user.application.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityRequestDto {

    private String title;
    private String content;
    private String writer;
    private Long accId; // Account FK (글쓴이 ID) - 옵션
}
