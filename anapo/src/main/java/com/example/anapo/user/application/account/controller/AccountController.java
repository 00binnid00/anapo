package com.example.anapo.user.application.account.controller;

import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.application.account.service.AccountService;
import com.example.anapo.user.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // 회원가입
    @PostMapping("/join")
    public Account joinUser(@RequestBody AccountDto accountDto) {
        Account account = accountService.join(accountDto);
        if (account == null) {
            throw new RuntimeException("이미 존재하는 아이디입니다."); // 실패 메시지 처리
        }
        return account;
    }


    // 로그인
    @PostMapping("/login")
    public Account loginUser(@RequestBody AccountDto accountDto) {
        Account account = accountService.login(accountDto);
        if (account == null) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다."); // 실패 메시지 처리
        }
        return account;
    }
}