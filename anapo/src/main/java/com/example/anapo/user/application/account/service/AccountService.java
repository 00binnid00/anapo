package com.example.anapo.user.application.account.service;

import com.example.anapo.user.DataNotFoundException;
import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userReservationService")
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    // DB 저장 트랜잭션 적용
    @Transactional
    public  Account create(AccountDto accountDto) {
        // account 객체 생성
        Account account = new Account(
                accountDto.getUserPassword(),
                accountDto.getUserName(),
                accountDto.getUserId(),
                accountDto.getUserNumber(),
                accountDto.getBirth(),
                accountDto.getSex());
        // DB에 저장
        return accountRepository.save(account);
    }

    public Account getUser(String userName) {
        Optional<Account> user = accountRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user.orElse(null);
    }

    public Account login(String userId, String userPassword) {
        Optional<Account> user = accountRepository.findByUserId(userId);
        if (user.isPresent()) {
            if (user.get().getUserPassword().equals(userPassword)) {
                return user.get();  // 로그인 성공
            }
        }
        return null;  // 로그인 실패
    }

    // 중복 체크 메서드
    public boolean existsByUserName(String userName) {
        return accountRepository.existsByUserName(userName);
    }
}