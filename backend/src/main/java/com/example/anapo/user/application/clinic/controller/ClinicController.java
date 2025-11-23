package com.example.anapo.user.application.clinic.controller;

import com.example.anapo.user.application.clinic.dto.ClinicDto;
import com.example.anapo.user.application.clinic.service.ClinicService;
import com.example.anapo.user.domain.clinic.entity.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    // 진료 내역 등록
    @PostMapping
    public ResponseEntity<?> createClinic(@RequestBody ClinicDto dto) {
        Clinic saved = clinicService.createClinic(dto);

        return ResponseEntity.ok(Map.of(
                "message", "진료 내역이 성공적으로 등록되었습니다.",
                "clinicId", saved.getId(),
                "doctorName", saved.getDoctorName(),
                "diagnosis", saved.getDiagnosis(),
                "clinicDate", saved.getClinicDate()
        ));
    }

    // 회원별 진료 내역 조회
    @GetMapping("/user/{acc}")
    public ResponseEntity<List<Clinic>> getClinicsByUser(@PathVariable Long acc) {
        return ResponseEntity.ok(clinicService.getClinicsByUser(acc));
    }

    // 병원별 진료 내역 조회
    @GetMapping("/hospital/{hos}")
    public ResponseEntity<List<Clinic>> getClinicsByHospital(@PathVariable Long hos) {
        return ResponseEntity.ok(clinicService.getClinicsByHospital(hos));
    }
}
