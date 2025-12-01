package com.example.anapo.user.application.hospital.controller;

import com.example.anapo.user.application.hospital.service.HospitalStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospital/stats")
@RequiredArgsConstructor
public class HospitalStaatsController {

    private final HospitalStatsService statsService;

    @GetMapping("/{hospitalId}")
    public ResponseEntity<?> getStats(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(statsService.getHospitalStats(hospitalId));
    }
}
