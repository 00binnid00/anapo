package com.example.anapo.user.application.hospital.service;

import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    // 병원 전체 조회
    public List<HospitalDto> getAllHospitals() {
        return hospitalRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 병원 이름으로 검색
    public List<HospitalDto> searchByName(String name) {
        return hospitalRepository.findByHosNameContaining(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 엔티티 -> DTO 변환
    private HospitalDto convertToDto(@Valid @RequestBody Hospital hospital) {
        return HospitalDto.builder()
                .id(hospital.getId())
                .hosName(hospital.getHosName())
                .hosAddress(hospital.getHosAddress())
                .hosNumber(hospital.getHosNumber())
                .hosTime(hospital.getHosTime())
                .build();
    }
}

