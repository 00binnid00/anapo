package com.example.anapo.user.application.hospital.service;

import com.example.anapo.user.application.hospital.dto.HosCreateDto;
import com.example.anapo.user.application.hospital.dto.HosUpdateDto;
import com.example.anapo.user.application.hospital.dto.HospitalDisDto;
import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.domain.department.entity.Department;
import com.example.anapo.user.domain.hospital.entity.HospitalDepartment;
import com.example.anapo.user.domain.department.repository.DepartmentRepository;
import com.example.anapo.user.domain.hospital.repository.HospitalDepartmentRepository;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final HospitalDepartmentRepository hospitalDepartmentRepository;

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

    // 병원 정보 등록
    @Transactional
    public Hospital createHospital(HosCreateDto dto) {

        // 1) 병원 먼저 저장
        Hospital hospital = Hospital.builder()
                .hosName(dto.getHosName())
                .hosAddress(dto.getHosAddress())
                .hosNumber(dto.getHosNumber())
                .hosEmail(dto.getHosEmail())
                .hosTime(dto.getHosTime())
                .hosLat(dto.getHosLat())
                .hosLng(dto.getHosLng())
                .build();

        hospitalRepository.save(hospital);

        // 2) 진료과 매핑 저장
        if (dto.getDepartments() != null) {
            for (Long deptId : dto.getDepartments()) {

                Department dept = departmentRepository.findById(deptId)
                        .orElseThrow(() ->
                                new RuntimeException("존재하지 않는 진료과 ID: " + deptId));

                HospitalDepartment mapping =
                        new HospitalDepartment(hospital, dept);

                hospitalDepartmentRepository.save(mapping);
            }
        }

        return hospital;
    }

    // 병원에 진료과 추가
    @Transactional
    public void addDepartments(Long hosId, List<Long> departmentIds) {

        Hospital hospital = hospitalRepository.findById(hosId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 병원입니다."));

        for (Long deptId : departmentIds) {

            Department department = departmentRepository.findById(deptId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 진료과 ID: " + deptId));

            HospitalDepartment hd = new HospitalDepartment(hospital, department);
            hospitalDepartmentRepository.save(hd);
        }
    }

    // 병원 정보 수정
    @Transactional
    public Hospital updateHospital(Long hosId, HosUpdateDto dto) {
        Hospital hospital = hospitalRepository.findById(hosId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원입니다."));

        hospital.updateInfo(
                dto.getHosName(),
                dto.getHosAddress(),
                dto.getHosEmail(),
                dto.getHosNumber(),
                dto.getHosLat(),
                dto.getHosLng()
        );

        return hospital;
    }

    // 해당 병원의 진료과목 검색
    public List<String> getDepartmentsByHospital(Long hospitalId) {

        List<HospitalDepartment> list =
                hospitalDepartmentRepository.findByHospitalId(hospitalId);

        return list.stream()
                .map(hd -> hd.getDepartment().getDeptName())  // 진료과 이름만 추출
                .collect(Collectors.toList());
    }

    // 진료과 기반 병원 검색
    public List<HospitalDto> searchByDepartment(Long departmentId) {
        return hospitalRepository.findByDepartment(departmentId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /*------------------------------------------------------------------------------------------*/
// 사용자 위치 기반 반경 병원 검색
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
            .filter(h -> h.getDistance() <= 1.0)
            .sorted(Comparator.comparingDouble(HospitalDisDto::getDistance))
            .collect(Collectors.toList());
}

    // 거리 계산
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int R = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}