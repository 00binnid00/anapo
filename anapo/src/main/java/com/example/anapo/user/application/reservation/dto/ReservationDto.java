package com.example.anapo.user.application.reservation.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {

    private LocalDateTime  reserDate;   // 예약 날짜 및 시간 (선택형)

    private String department; // 진료 과목 (선택형)

    private Long acc;       // 사용자 ID

    private Long hos;       // 병원 ID
}
