package com.example.anapo.user.application.reservation.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class ReservationDto {
    private Date reserDate;

    private Boolean reserYesNo;

    private Boolean clinicYesNo;

    private Integer acc;

    private Integer hos;
}
