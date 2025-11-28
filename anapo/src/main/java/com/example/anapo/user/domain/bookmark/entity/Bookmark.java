package com.example.anapo.user.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 FK
    @Column(nullable = false)
    private Long userId;

    // 병원 FK
    @Column(nullable = false)
    private Long hospitalId;

    // 즐겨찾기 등록 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;
}