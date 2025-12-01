package com.example.anapo.user.application.hospital.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HosCreateDto {
    private String hosName;     // 병원 이름
    private String hosAddress;  // 병원 주소
    private String hosNumber;   // 병원 전화번호
    private String hosEmail;    // 병원 이메일
    private String hosTime;     // 영업 시간

    private List<Long> departments; // 진료과 ID 리스트
}
