package com.example.anapo.user.application.notice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 생성/수정용
// 공지 생성될 때 클라이언트에게서 넘어오는 데이터를 받는
// 제목, 콘텐츠, 작성자, 병원id
public class NoticeRequestDto {
    private String title;
    private String content;
    private String writer;
    private Long hospitalId;
}