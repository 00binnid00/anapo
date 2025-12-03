package com.example.anapo.user.domain.notification.repository;

import com.example.anapo.user.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 아직 미전송 + 시간이 된 알림 조회
    List<Notification> findBySendAtBeforeAndIsRead(LocalDateTime now, boolean isRead);

    // 특정 사용자의 알림 목록
    List<Notification> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
