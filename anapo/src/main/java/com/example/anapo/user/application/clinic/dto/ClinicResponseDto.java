package com.example.anapo.user.application.clinic.dto;

import com.example.anapo.user.domain.clinic.entity.Clinic;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClinicResponseDto {
    private Long id;
    private String diagnosis;
    private String doctorName;
    private String treatment;
    private LocalDateTime clinicDate;
    private Long reservationId;

    public ClinicResponseDto(Clinic clinic) {
        this.id = clinic.getId();
        this.diagnosis = clinic.getDiagnosis();
        this.doctorName = clinic.getDoctorName();
        this.treatment = clinic.getTreatment();
        this.clinicDate = clinic.getClinicDate();
        this.reservationId = clinic.getReservation().getId();
    }

}
