package com.example.anapo.admin.application.service;

import com.example.anapo.admin.application.dto.UserStatusUpdateDto;
import com.example.anapo.user.enums.AccountStatus;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHosUserService {

    private final HospitalRepository hospitalRepository;

    // 전체 병원 사용자 조회
    public List<Hospital> getAllHospitalUsers() {
        return hospitalRepository.findAll();
    }

    // 병원 사용자 계정 상태 변경
    public void updateHospitalUserStatus(Long hosId, UserStatusUpdateDto dto) {
        Hospital hos = hospitalRepository.findById(hosId)
                .orElseThrow(() -> new IllegalArgumentException("병원 사용자를 찾을 수 없습니다."));

        hos.setStatus(AccountStatus.valueOf(dto.getStatus().toUpperCase()));

        hospitalRepository.save(hos);
    }
}
