package com.example.anapo.admin.application.service;

import com.example.anapo.admin.application.dto.UserStatusUpdateDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.enums.AccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final AccountRepository accountRepository;

    // 전체 유저 목록 조회
    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    // 특정 유저 상태 변경
    public void updateUserStatus(Long accId, UserStatusUpdateDto dto) {

        // dto가 제대로 들어왔는지 확인
        if (dto == null || dto.getStatus() == null) {
            throw new IllegalArgumentException("status 값이 비어 있습니다.");
        }

        // 유저 조회
        Account account = accountRepository.findById(accId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + accId));

        // String → Enum 변환 (예외 처리 포함)
        AccountStatus status;
        try {
            status = AccountStatus.valueOf(dto.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 상태 값입니다: " + dto.getStatus());
        }

        // 상태 변경
        account.setStatus(status);

        accountRepository.save(account);
    }
}
