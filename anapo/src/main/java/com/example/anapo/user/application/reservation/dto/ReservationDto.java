package com.example.anapo.user.application.reservation.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ReservationDto {
    private Date reserDate;   // 예약 날짜 및 시간 (선택형)

    private String department; // 진료 과목 (선택형)

    private Integer acc;       // 사용자 ID

    private Integer hos;       // 병원 ID
}
