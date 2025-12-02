package com.example.anapo.admin.application.controller;

import com.example.anapo.admin.application.dto.UserStatusUpdateDto;
import com.example.anapo.admin.application.service.AdminHosUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hos-users")
@RequiredArgsConstructor
public class AdminHosUserController {

    private final AdminHosUserService service;

    // 병원 사용자 계정 전체 조회
    @GetMapping
    public ResponseEntity<?> getHospitalUsers() {
        return ResponseEntity.ok(service.getAllHospitalUsers());
    }

    // 병원 사용자 계정 상태 변경
    @PatchMapping("/{hosId}/status")
    public ResponseEntity<?> updateHospitalUserStatus(
            @PathVariable Long hosId,
            @RequestBody UserStatusUpdateDto dto
    ) {
        service.updateHospitalUserStatus(hosId, dto);
        return ResponseEntity.ok("병원 사용자 계정 상태가 변경되었습니다.");
    }
}
