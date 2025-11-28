package com.example.anapo.user.domain.reservation.entity;

import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.CustomLog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 증가
    private Long id; // 예약 고유 번호 (PK)
    
    @Column(nullable = false)
    private LocalDateTime reserDate; // 예약 날짜 및 시간

    @Column(nullable = false)
    private String department; // 진료 과목 (예: 내과, 치과 등)

    @Column(nullable = false)
    private Boolean reserYesNo; // 예약 여부 (true = 예약 완료)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc", nullable = false)
    private Account user; // 예약자 (회원)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hos", nullable = false)
    private Hospital hospital; // 병원

    // 생성자
    public Reservation(LocalDateTime  reserDate, String department, Boolean reserYesNo, Account user, Hospital hospital) {
        this.reserDate = reserDate;
        this.department = department;
        this.reserYesNo = reserYesNo;
        this.user = user;
        this.hospital = hospital;
    }

    public void changeReservation(LocalDateTime newDate, String newDepartment) {
        this.reserDate = newDate;
        this.department = newDepartment;
    }
}

