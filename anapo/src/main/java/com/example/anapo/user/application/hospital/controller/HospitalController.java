package com.example.anapo.user.application.hospital.controller;

import com.example.anapo.user.application.hospital.dto.HosCreateDto;
import com.example.anapo.user.application.hospital.dto.HosUpdateDto;
import com.example.anapo.user.application.hospital.dto.HospitalDisDto;
import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.application.hospital.service.HospitalSearchService;
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
    private final HospitalSearchService hospitalSearchService;


    // 병원 정보 등록
    @PostMapping
    public ResponseEntity<?> createHospital(@RequestBody HosCreateDto dto) {
        Hospital saved = hospitalService.createHospital(dto);

        return ResponseEntity.ok(Map.of(
                "message", "병원 등록 완료",
                "id", saved.getId()
        ));
    }

    // 병원 정보 수정
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

    // 병원 진료과목 추가
    @PostMapping("/{hosId}/departments")
    public ResponseEntity<?> addDepartmentsToHospital(
            @PathVariable Long hosId,
            @RequestBody Map<String, List<Long>> request
    ) {
        List<Long> departmentIds = request.get("departments");
        hospitalService.addDepartments(hosId, departmentIds);

        return ResponseEntity.ok(Map.of(
                "message", "진료과가 병원에 추가되었습니다.",
                "hospitalId", hosId,
                "departments", departmentIds
        ));
    }

/*------------------------------------------------------------------------------------------*/

    // 전체 병원 목록 조회
    @GetMapping
    public List<HospitalDto> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

/*------------------------------------------------------------------------------------------*/

    // 병원 위도, 경도 구하기
    @GetMapping("/near")
    public ResponseEntity<List<HospitalDisDto>> getNearby(
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        List<HospitalDisDto> result = hospitalSearchService.getNearbyHospitals(lat, lng);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/address")
    public ResponseEntity<?> searchByAddress(@RequestParam String address) {
        List<HospitalDisDto> result = hospitalService.searchByAddress(address);
        return ResponseEntity.ok(result);
    }

    
    // 병원 삭제
    @DeleteMapping("/{hosId}")
    public ResponseEntity<?> deleteByHospitals(@PathVariable Long hosId) {
        try {
            hospitalService.deleteHospital(hosId);

            return ResponseEntity.ok(Map.of(
                    "message", "병원이 정상적으로 삭제되었습니다.",
                    "hosId", hosId
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
