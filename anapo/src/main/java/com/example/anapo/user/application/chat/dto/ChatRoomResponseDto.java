package com.example.anapo.user.application.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ChatRoomResponseDto {
    private Long roomId;
    private Long userId;
    private Long hospitalId;
    private String lastMessage;
    private String lastTime;
}