package com.example.anapo.user.application.hospital.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HosUpdateDto {

    private String hosName;      // 병원 이름
    private String hosAddress;   // 주소
    private String hosEmail;     // 이메일
    private String hosNumber;    // 전화번호
    private Double hosLat;       // 위도
    private Double hosLng;       // 경도
}