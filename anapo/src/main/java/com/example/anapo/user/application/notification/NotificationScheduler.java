package com.example.anapo.user.application.notification;

import com.example.anapo.user.domain.notification.entity.Notification;
import com.example.anapo.user.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationRepository notificationRepository;

    @Scheduled(fixedRate = 60000) // 1분마다 체크
    public void sendNotifications() {

        LocalDateTime now = LocalDateTime.now();

        List<Notification> dueList =
                notificationRepository.findBySendAtBeforeAndIsRead(now, false);

        for (Notification n : dueList) {
            // 여기에 실제 push/send 기능 넣기
            System.out.println("알림 발송: " + n.getMessage());

            n.setRead(true); // 발송됨 == 읽은 것으로 처리 (또는 'sent' 필드 만들기 가능)
            notificationRepository.save(n);
        }
    }
}
