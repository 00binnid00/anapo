package com.example.anapo.user.domain.community.entity;

import com.example.anapo.user.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;     // 제목

    @Column(columnDefinition = "TEXT")
    private String content;   // 내용

    private String writer;    // 작성자 이름

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;  // 작성자 FK (옵션)

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
