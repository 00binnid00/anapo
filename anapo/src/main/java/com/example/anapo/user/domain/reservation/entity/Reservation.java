package com.example.anapo.user.domain.reservation.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.CustomLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 증가
    private Long id; // 예약 고유 번호 (PK)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date reserDate; // 예약 날짜 및 시간

    @Column(nullable = false)
    private String department; // 진료 과목 (예: 내과, 치과 등)

    @Column(nullable = false)
    private Boolean reserYesNo; // 예약 여부 (true = 예약 완료)

    @Column(nullable = false)
    private Integer acc; // 예약자 (회원 ID)

    @Column(nullable = false)
    private Integer hos; // 병원 ID

    // 생성자
    public Reservation(Date reserDate, String department, Boolean reserYesNo, Integer acc, Integer hos) {
        this.reserDate = reserDate;
        this.department = department;
        this.reserYesNo = reserYesNo;
        this.acc = acc;
        this.hos = hos;
    }
}
