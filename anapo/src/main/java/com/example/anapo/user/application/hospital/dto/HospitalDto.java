package com.example.anapo.user.application.hospital.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HospitalDto {

    private Long id;

    @NotEmpty
    private String hosName;

    @NotEmpty
    private String hosAddress;

    @NotEmpty
    private String hosNumber;

    @NotEmpty
    private String hosEmail;

    @NotEmpty
    private String hosTime;

}
