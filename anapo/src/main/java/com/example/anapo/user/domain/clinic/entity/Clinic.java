package com.example.anapo.user.domain.clinic.entity;

import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 진료 내역 ID

    @Column(nullable = false)
    private LocalDateTime clinicDate; // 진료 일자

    @Column(nullable = false, length = 255)
    private String diagnosis; // 진단명

    @Column(nullable = false, columnDefinition = "TEXT")
    private String treatment; // 진료 내용

    @Column(nullable = false, length = 255)
    private String doctorName; // 담당 의사 이름

    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc", nullable = false)
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hos", nullable = false)
    private Hospital hospital;

    // 생성자
    public Clinic(LocalDateTime clinicDate, String diagnosis, String treatment, String doctorName,
                  Reservation reservation, Account user, Hospital hospital) {
        this.clinicDate = clinicDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.doctorName = doctorName;
        this.reservation = reservation;
        this.user = user;
        this.hospital = hospital;
    }
}
