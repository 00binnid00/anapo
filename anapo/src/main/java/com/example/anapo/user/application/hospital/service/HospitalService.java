package com.example.anapo.user.application.hospital.service;

import com.example.anapo.user.application.hospital.dto.HospitalCreateDto;
import com.example.anapo.user.application.hospital.dto.HospitalDisDto;
import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Comparator;
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
    private HospitalDto convertToDto(Hospital hospital) {
        return HospitalDto.builder()
                .id(hospital.getId())
                .hosName(hospital.getHosName())
                .hosAddress(hospital.getHosAddress())
                .hosNumber(hospital.getHosNumber())
                .hosTime(hospital.getHosTime())
                .build();
    }

/*------------------------------------------------------------------------------------------*/

public Hospital createHospital(HospitalCreateDto dto) {
    Hospital hospital = Hospital.builder()
            .hosName(dto.getHosName())
            .hosAddress(dto.getHosAddress())
            .hosNumber(dto.getHosNumber())
            .hosTime(dto.getHosTime())
            .hosLat(dto.getHosLat())
            .hosLng(dto.getHosLng())
            .build();

    return hospitalRepository.save(hospital);
}

/*------------------------------------------------------------------------------------------*/

    // 사용자 위치 기준 반경 1km 병원 찾기
    public List<HospitalDisDto> getNearbyHospitals(double userLat, double userLng) {

        List<Hospital> hospitals = hospitalRepository.findAll();

        return hospitals.stream()
                .map(h -> {
                    double distance = calculateDistance(
                            userLat, userLng,
                            h.getHosLat(), h.getHosLng()
                    );
                    return new HospitalDisDto(h, distance);
                })
                .filter(h -> h.getDistance() <= 1.0) // 반경 1km 이하만 필터링
                .sorted(Comparator.comparingDouble(HospitalDisDto::getDistance)) // 가까운 순 정렬
                .collect(Collectors.toList());
    }

    // 거리 계산
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371; // 지구 반지름(km)

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 거리(km)
    }
}

