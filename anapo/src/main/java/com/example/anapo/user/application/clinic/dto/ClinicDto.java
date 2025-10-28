package com.example.anapo.user.application.clinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClinicDto {
    private LocalDateTime clinicDate;
    private String diagnosis;
    private String treatment;
    private String doctorName;
    private Long reservationId;
    private Long acc; // Account FK
    private Long hos; // Hospital FK
}
