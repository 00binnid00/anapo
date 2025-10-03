package com.example.anapo.user.application.hospital.controller;

import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.application.hospital.service.HospitalService;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    // 전체 병원 목록 조회
    @GetMapping
    public List<HospitalDto> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    // 이름으로 병원 검색
    @GetMapping("/search")
    public List<HospitalDto> searchHospitals(@RequestParam String name) {
        return hospitalService.searchByName(name);
    }
}
