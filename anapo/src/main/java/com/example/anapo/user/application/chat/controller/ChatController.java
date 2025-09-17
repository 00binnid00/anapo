package com.example.anapo.user.application.chat.controller;

import com.example.anapo.user.application.chat.dto.ChatDto;
import com.example.anapo.user.application.chat.service.ChatService;
import com.example.anapo.user.domain.chat.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/join")
    public Chat joinUser(@RequestBody ChatDto chatDto){
        return ChatService.join(chatDto);
    }
}
