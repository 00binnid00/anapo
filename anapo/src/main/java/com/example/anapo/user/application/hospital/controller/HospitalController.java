package com.example.anapo.user.application.hospital.controller;

import com.example.anapo.user.application.hospital.dto.HosCreateDto;
import com.example.anapo.user.application.hospital.dto.HosUpdateDto;
import com.example.anapo.user.application.hospital.dto.HospitalDisDto;
import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.application.hospital.service.HospitalService;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;


    @PostMapping
    public ResponseEntity<?> createHospital(@RequestBody HosCreateDto dto) {
        Hospital saved = hospitalService.createHospital(dto);

        return ResponseEntity.ok(Map.of(
                "message", "병원 등록 완료",
                "id", saved.getId()
        ));
    }

    @PatchMapping("/HosUpdate/{hosId}")
    public ResponseEntity<?> updateHospital(
            @PathVariable Long hosId,
            @RequestBody HosUpdateDto dto
    ) {
        Hospital updated = hospitalService.updateHospital(hosId, dto);

        return ResponseEntity.ok(
                Map.of(
                        "message", "병원 정보가 수정되었습니다.",
                        "id", updated.getId(),
                        "hosName", updated.getHosName(),
                        "hosAddress", updated.getHosAddress(),
                        "hosEmail", updated.getHosEmail(),
                        "hosNumber", updated.getHosNumber(),
                        "hosLat", updated.getHosLat(),
                        "hosLng", updated.getHosLng()
                )
        );
    }

/*------------------------------------------------------------------------------------------*/

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

/*------------------------------------------------------------------------------------------*/

    // 병원 위도, 경도 구하기
    @GetMapping("/near")
    public ResponseEntity<List<HospitalDisDto>> getNearby(
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        List<HospitalDisDto> result = hospitalService.getNearbyHospitals(lat, lng);
        return ResponseEntity.ok(result);
    }
}
