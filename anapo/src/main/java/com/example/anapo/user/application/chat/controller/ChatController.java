package com.example.anapo.user.application.chat.controller;

import com.example.anapo.user.application.chat.dto.ChatMessageDto;
import com.example.anapo.user.application.chat.service.ChatService;
import com.example.anapo.user.domain.chat.entity.ChatMessage;
import com.example.anapo.user.domain.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 상담방 생성
    @PostMapping("/room")
    public ChatRoom createRoom(@RequestParam Long userId, @RequestParam Long hospitalId) {
        return chatService.getOrCreateRoom(userId, hospitalId);
    }

    // 메세지 보내기
    @PostMapping("/message")
    public ChatMessage sendMessage(@RequestBody ChatMessageDto dto) {
        return chatService.sendMessage(dto);
    }

    // 실시간
    @GetMapping(value = "/subscribe/{roomId}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long roomId) {
        return chatService.subscribe(roomId);
    }

    // 과거 메세지 조회
    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        return chatService.getMessages(roomId);
    }
}