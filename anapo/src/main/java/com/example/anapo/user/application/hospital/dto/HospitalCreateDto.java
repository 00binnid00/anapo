package com.example.anapo.user.application.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HospitalCreateDto {
    private String hosName;
    private String hosAddress;
    private String hosNumber;
    private String hosTime;
    private Double hosLat;
    private Double hosLng;
}
