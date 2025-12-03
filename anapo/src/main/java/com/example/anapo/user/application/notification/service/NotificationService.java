package com.example.anapo.user.application.notification.service;

import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.notification.entity.Notification;
import com.example.anapo.user.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;

    // 알림 생성
    public void createNotification(Long userId, String title, String message, LocalDateTime sendAt) {
        Account acc = accountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Notification notif = Notification.builder()
                .account(acc)
                .title(title)
                .message(message)
                .createdAt(LocalDateTime.now())
                .sendAt(sendAt)
                .isRead(false)
                .build();

        notificationRepository.save(notif);
    }

    // 읽음 처리
    public void readNotification(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("알림 없음"));

        n.setRead(true);
        notificationRepository.save(n);
    }

    // 조회
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByAccountIdOrderByCreatedAtDesc(userId);
    }
}
