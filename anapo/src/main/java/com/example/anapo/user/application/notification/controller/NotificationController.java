package com.example.anapo.user.application.notification.controller;

import com.example.anapo.user.application.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 사용자 알림 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    // 읽음 처리
    @PatchMapping("/{id}/read")
    public ResponseEntity<?> read(@PathVariable Long id) {
        notificationService.readNotification(id);
        return ResponseEntity.ok("알림 읽음 처리 완료");
    }
}
