package com.example.anapo.user.domain.notification.entity;

import com.example.anapo.user.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acc_id", nullable = false)
    private Account account; // 알림 받을 사용자

    private String title;       // 알림 제목
    private String message;     // 알림 내용

    private boolean isRead;     // 읽음 여부

    private LocalDateTime createdAt;  // 알림 생성 시간
    private LocalDateTime sendAt;     // 실제 알림을 보내야 하는 시간 (예약 기준)

}
