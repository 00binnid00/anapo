package com.example.anapo.user.application.hospital.service;

import com.example.anapo.user.application.hospital.dto.HospitalDisDto;
import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalDepartmentRepository;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/* 병원 검색/조회 관련 기능만 담당 */
/* 병원 이름, 진료과, 거리 기반 검색 */
/* 병원별 진료과 조회 */
@Service
@RequiredArgsConstructor
public class HospitalSearchService {

    private final HospitalRepository hospitalRepository;
    private final HospitalDepartmentRepository hospitalDepartmentRepository;

    // 이름 검색
    public List<HospitalDto> searchByName(String name) {
        return hospitalRepository.findByHosNameContaining(name)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 진료과 검색
    public List<HospitalDto> searchByDepartment(Long departmentId) {
        return hospitalRepository.findByDepartment(departmentId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

/*--------------------------------------------------------------------------------------------*/

    // 병원별 진료과 조회
    public List<String> getDepartmentsByHospital(Long hospitalId) {
        return hospitalDepartmentRepository.findByHospitalId(hospitalId)
                .stream()
                .map(hd -> hd.getDepartment().getDeptName())
                .collect(Collectors.toList());
    }

/*--------------------------------------------------------------------------------------------*/

    // 거리 기반 검색
    public List<HospitalDisDto> getHospitalsByDistance(double userLat, double userLng) {
        return hospitalRepository.findAll()
                .stream()
                .map(h -> new HospitalDisDto(h,
                        calculateDistance(userLat, userLng, h.getHosLat(), h.getHosLng())))
                .sorted(Comparator.comparingDouble(HospitalDisDto::getDistance))
                .collect(Collectors.toList());
    }

    // 반경 1km 검색
    public List<HospitalDisDto> getNearbyHospitals(double userLat, double userLng) {
        return hospitalRepository.findAll()
                .stream()
                .map(h -> new HospitalDisDto(h,
                        calculateDistance(userLat, userLng, h.getHosLat(), h.getHosLng())))
                .filter(h -> h.getDistance() <= 1.0)
                .sorted(Comparator.comparingDouble(HospitalDisDto::getDistance))
                .collect(Collectors.toList());
    }

    // 거리 계산
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // 지구 반지름
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);

        return R * (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
    }


    // DTO로 변환하는 로직
    private HospitalDto convertToDto(Hospital hospital) {
        return HospitalDto.builder()
                .id(hospital.getId())
                .hosName(hospital.getHosName())
                .hosAddress(hospital.getHosAddress())
                .hosNumber(hospital.getHosNumber())
                .hosTime(hospital.getHosTime())
                .build();
    }
}
