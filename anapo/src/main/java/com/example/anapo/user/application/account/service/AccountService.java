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

    private static final AccountRepository accountRepository = null;

    // DB 저장 트랜잭션 적용
    @Transactional
    public static Account create(AccountDto accountDto) {
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
        Optional<Account> user = AccountRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user.orElse(null);
    }

    public Account login(String name, String userPassword) {
    }

//    // 회원가입
//    public Account join(AccountDto accountDto){
//
//        // DB에서 같은 아이디가 이미 존재하는지 확인
//        Optional<Account> foundAccount = accountRepository.findByUserId(accountDto.getUserId());
//
//        // 이미 존재하면 예외 발생 → 중복 방지
//        if (foundAccount.isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
//        }
//
//        // account 객체 생성
//        Account account = new Account(
//                accountDto.getUserPassword(),
//                accountDto.getName(),
//                accountDto.getUserId(),
//                accountDto.getUserNumber(),
//                accountDto.getBirth(),
//                accountDto.getSex());
//        // DB에 저장
//        return accountRepository.save(account);
//    }
//
//    // 로그인
//    public Account login(AccountDto accountDto) {
//        // DB에서 아이디로 사용자 조회
//        Optional<Account> foundAccount = accountRepository.findByUserId(accountDto.getUserId());
//
//        // 아이디가 없거나 비밀번호 틀리면 null 반환
//        if (foundAccount.isEmpty()) return null;
//
//        Account account = foundAccount.get();
//        if (!account.getUserPassword().equals(accountDto.getUserPassword())) {
//            return null;
//        }
//
//        // 로그인 성공하면 Account 객체 반환
//        return account;
//    }
}
