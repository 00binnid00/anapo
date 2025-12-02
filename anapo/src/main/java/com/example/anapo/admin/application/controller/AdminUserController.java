package com.example.anapo.admin.application.controller;

import com.example.anapo.admin.application.dto.UserStatusUpdateDto;
import com.example.anapo.admin.application.service.AdminUserService;
import com.example.anapo.user.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // 전체 유저 목록 조회
    @GetMapping
    public ResponseEntity<List<Account>> getAllUsers() {
        List<Account> users = adminUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 유저 상태 변경 (ex: ACTIVE → SUSPENDED)
    @PatchMapping("/{accId}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long accId,
            @RequestBody UserStatusUpdateDto dto
    ) {
        adminUserService.updateUserStatus(accId, dto);
        return ResponseEntity.ok("회원 상태가 변경되었습니다.");
    }
}
