package com.example.anapo.user.domain.reservation.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.CustomLog;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Reservation {
    @Column(nullable = false)
    private Date reserDate;

    @Column(nullable = false)
    private Boolean reserYesNo;

    @Column(nullable = false)
    private Boolean clinicYesNo;

    @Column(nullable = false)
    private Integer acc;

    @Column(nullable = false)
    private Integer hos;

    public Reservation(Date reserDate, Boolean reserYesNo, Boolean clinicYesNo, Integer acc, Integer hos){
        this.reserDate = reserDate;
        this.reserYesNo = reserYesNo;
        this.clinicYesNo = clinicYesNo;
        this.acc = acc;
        this.hos = hos;
    }
}
