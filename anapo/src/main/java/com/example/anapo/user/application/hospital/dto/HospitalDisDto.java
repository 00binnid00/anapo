package com.example.anapo.user.application.hospital.dto;

import com.example.anapo.user.domain.hospital.entity.Hospital;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class HospitalDisDto {

    private Long id;

    @NotEmpty
    private String hosName;

    @NotEmpty
    private String hosAddress;

    @NotEmpty
    private String hosNumber;

    @NotEmpty
    private String hosTime;

    @NotEmpty
    private double hosLat;

    @NotEmpty
    private double hosLng;

    @NotEmpty
    private double distance;  // 사용자와의 거리 km

    public HospitalDisDto(Hospital hospital, double distance) {
        this.id = hospital.getId();
        this.hosName = hospital.getHosName();
        this.hosAddress = hospital.getHosAddress();
        this.hosNumber = hospital.getHosNumber();
        this.hosTime = hospital.getHosTime();
        this.hosLat = hospital.getHosLat();
        this.hosLng = hospital.getHosLng();
        this.distance = distance;
}
}
