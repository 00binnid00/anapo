package com.example.anapo.user.application.chat.service;

import com.example.anapo.user.application.chat.dto.ChatMessageDto;
import com.example.anapo.user.domain.chat.entity.ChatMessage;
import com.example.anapo.user.domain.chat.entity.ChatRoom;
import com.example.anapo.user.domain.chat.repository.ChatMessageRepository;
import com.example.anapo.user.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository roomRepository;
    private final ChatMessageRepository messageRepository;
    private final Map<Long, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    // 상담방 생성 및 기존방 찾기
    public ChatRoom getOrCreateRoom(Long userId, Long hospitalId) {
        return roomRepository.findByUserIdAndHospitalId(userId, hospitalId)
                .orElseGet(() -> roomRepository.save(
                        ChatRoom.builder()
                                .userId(userId)
                                .hospitalId(hospitalId)
                                .build()));
    }

    // 메세지 저장 + sse 전송
    public ChatMessage sendMessage(ChatMessageDto dto) {

        ChatMessage saved = messageRepository.save(ChatMessage.create(
                dto.getRoomId(),
                dto.getSenderId(),
                dto.getSenderType(),
                dto.getMessage()
        ));

        // 이 방을 사용하고 있는 모든 SSE 연결에게 메시지 push
        List<SseEmitter> roomEmitters = emitters.getOrDefault(dto.getRoomId(), new ArrayList<>());

        for (SseEmitter emitter : roomEmitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("chat")
                        .data(saved));
            } catch (Exception e) {
                emitter.complete();
            }
        }

        return saved;
    }

    // sse 구독 -> 채팅방 사용
    public SseEmitter subscribe(Long roomId) {

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitters.computeIfAbsent(roomId, (key) -> new ArrayList<>()).add(emitter);

        emitter.onCompletion(() -> emitters.get(roomId).remove(emitter));
        emitter.onTimeout(() -> emitters.get(roomId).remove(emitter));

        return emitter;
    }

    // 채팅 메세지 조회
    public List<ChatMessage> getMessages(Long roomId) {
        return messageRepository.findAllByRoomIdOrderBySentAt(roomId);
    }
}