package com.example.anapo.user.application.hospital.controller;

import com.example.anapo.user.application.hospital.dto.HospitalDisDto;
import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.application.hospital.service.HospitalSearchService;
import com.example.anapo.user.application.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospitals/search")
@RequiredArgsConstructor
public class HospitalSearchController {

    private final HospitalSearchService hospitalSearchService;

    // 이름으로 병원 검색
    @GetMapping("/name")
    public List<HospitalDto> searchHospitalsByName(@RequestParam String name) {
        return hospitalSearchService.searchByName(name);
    }

    // 진료과로 병원 검색
    @GetMapping("/department")
    public List<HospitalDto> searchHospitalsByDepartment(@RequestParam Long departmentId) {
        return hospitalSearchService.searchByDepartment(departmentId);
    }

    // 해당 병원의 진료과목 검색
    @GetMapping("/{hosId}/departments")
    public ResponseEntity<?> getHospitalDepartments(@PathVariable Long hosId) {

        List<String> departments = hospitalSearchService.getDepartmentsByHospital(hosId);

        return ResponseEntity.ok(Map.of(
                "hospitalId", hosId,
                "departments", departments
        ));
    }

    // 위치 기반 거리순 검색 (필요하면 사용~!)
    @GetMapping("/distance")
    public List<HospitalDisDto> searchByDistance(
            @RequestParam double userLat,
            @RequestParam double userLng
    ) {
        return hospitalSearchService.getHospitalsByDistance(userLat, userLng);
    }
}