package com.example.anapo.user.application.account.service;

import com.example.anapo.user.DataNotFoundException;
import com.example.anapo.user.application.account.controller.UserCreateForm;
import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository = null;

    // DB 저장 트랜잭션 적용
    @Transactional
    public  Account create(AccountDto accountDto) {
        // account 객체 생성
        Account account = new Account(
                accountDto.getUserPassword(),
                accountDto.getName(),
                accountDto.getUserId(),
                accountDto.getUserNumber(),
                accountDto.getBirth(),
                accountDto.getSex());
        // DB에 저장
        return accountRepository.save(account);
    }

    public Account getUser(String username) {
        Optional<Account> user = accountRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user.orElse(null);
    }

    public Account login(String name, String userPassword) {
        return null;
    }
}