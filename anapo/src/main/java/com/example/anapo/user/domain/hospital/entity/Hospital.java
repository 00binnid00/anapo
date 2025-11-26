package com.example.anapo.user.domain.hospital.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public final class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hos_name", nullable = false)
    private String hosName;

    @Column(name = "hos_address", nullable = false)
    private String hosAddress;

    @Column(name = "hos_number", nullable = false)
    private String hosNumber;

    @Column(name = "hos_time", nullable = false)
    private String hosTime;

    @Column(name = "hos_lat", nullable = false)
    private double hosLat;      // 위도

    @Column(name = "hos_lng", nullable = false)
    private double hosLng;      // 경도

    public Hospital(String hosName, String hosAddress, String hosNumber, String hosTime,
                    double hosLat, double hosLng) {
        this.hosName = hosName;
        this.hosAddress = hosAddress;
        this.hosNumber = hosNumber;
        this.hosTime = hosTime;
        this.hosLat = hosLat;
        this.hosLng = hosLng;
    }
}
