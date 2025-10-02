package com.example.anapo.user.domain.hospital.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Hospital {

    @Column(nullable = false)
    private String hosName;

    @Column(nullable = false)
    private String hosAdress;

    @Column(nullable = false)
    private String hosNumber;

    @Column(nullable = false)
    private String hosTime;

    public Hospital(String hosName, String hosAdress, String hosNumber, String hosTime) {
        this.hosName = hosName;
        this.hosAdress = hosAdress;
        this.hosNumber = hosNumber;
        this.hosTime = hosTime;
    }
}
