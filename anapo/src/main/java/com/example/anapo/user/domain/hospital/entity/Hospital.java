package com.example.anapo.user.domain.hospital.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hosName;

    @Column(nullable = false)
    private String hosAddress;

    @Column(nullable = false)
    private String hosNumber;

    @Column(nullable = false)
    private String hosTime;

    public Hospital(String hosName, String hosAddress, String hosNumber, String hosTime) {
        this.hosName = hosName;
        this.hosAddress = hosAddress;
        this.hosNumber = hosNumber;
        this.hosTime = hosTime;
    }
}
