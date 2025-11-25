package com.example.anapo.user.domain.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private Long userId;     // 일반 회원 (account_id)

    @NotEmpty
    private Long hospitalId; // 병원 사용자 (hospital_id)
}
