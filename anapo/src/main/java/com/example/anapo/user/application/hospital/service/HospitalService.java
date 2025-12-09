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
    private final GeocodingService geocodingService;
    private final HospitalSearchService hospitalSearchService;


    // 병원 전체 조회
    public List<HospitalDto> getAllHospitals() {
        return hospitalRepository.findAll()
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
                .hosEmail(hospital.getHosEmail())
                .hosTime(hospital.getHosTime())
                .build();
    }

/*------------------------------------------------------------------------------------------*/

    @Transactional
    public Hospital createHospital(HosCreateDto dto) {

        // 주소 → 위도/경도 변환
        double lat = 0.0;
        double lng = 0.0;

        double[] coords = geocodingService.geocode(dto.getHosAddress());

        // null-safe 처리 추가 (주소 검색 실패 시에도 병원 등록이 가능하도록)
        if (coords != null) {
            lat = coords[0];
            lng = coords[1];
        }

        // 병원 저장 (lat/lng 자동 삽입)
        Hospital hospital = Hospital.builder()
                .hosName(dto.getHosName())
                .hosAddress(dto.getHosAddress())
                .hosNumber(dto.getHosNumber())
                .hosEmail(dto.getHosEmail())
                .hosTime(dto.getHosTime())
                .hosLat(lat)   // 위도 자동 계산
                .hosLng(lng)   // 경도 자동 계산
                .build();

        hospitalRepository.save(hospital);

        // 진료과 매핑 저장
        if (dto.getDepartments() != null) {
            for (Long deptId : dto.getDepartments()) {

                Department dept = departmentRepository.findById(deptId)
                        .orElseThrow(() -> new RuntimeException("존재하지 않는 진료과 ID: " + deptId));

                HospitalDepartment mapping = new HospitalDepartment(hospital, dept);
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


    // 병원 삭제
    @Transactional
    public void deleteHospital(Long hosId) {

        // 1. 병원 존재 여부 확인
        Hospital hospital = hospitalRepository.findById(hosId)
                .orElseThrow(() -> new IllegalArgumentException("해당 병원이 존재하지 않습니다."));

        // 2. 매핑된 진료과(HospitalDepartment) 모두 삭제
        hospitalDepartmentRepository.deleteByHospitalId(hosId);

        // 3. 병원 삭제
        hospitalRepository.delete(hospital);
    }

/*------------------------------------------------------------------------------------------*/

    // 주소 -> 위도/경도 변환
    public List<HospitalDisDto> searchByAddress(String address) {

        double[] coords = geocodingService.geocode(address);

        if (coords == null) {
            // 주소 검색 안되면 빈 리스트 반환
            return List.of();
        }

        double lat = coords[0];
        double lng = coords[1];

        // 거리 기반 병원 검색으로 넘기는 단계
        return hospitalSearchService.getHospitalsByDistance(lat, lng);
    }


}