package com.example.anapo.user.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private Long roomId;

    @NotEmpty
    private Long senderId; // 보낸 사람 id

    @NotEmpty
    private String senderType;       // "USER" or "HOSPITAL"

    @NotEmpty
    private String message;

    @NotEmpty
    private LocalDateTime sentAt;

    public static ChatMessage create(Long roomId, Long senderId, String senderType, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setSenderId(senderId);
        chatMessage.setSenderType(senderType);
        chatMessage.setMessage(message);
        chatMessage.setSentAt(LocalDateTime.now());
        return chatMessage;
    }
}
