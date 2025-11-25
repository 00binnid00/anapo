package com.example.anapo.user.application.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessageDto {
    private Long roomId;         // 채팅방 ID
    private Long senderId;       // 보낸 사람 ID
    private String senderType;   // "USER" or "HOSPITAL"
    private String message;      // 내용
}
