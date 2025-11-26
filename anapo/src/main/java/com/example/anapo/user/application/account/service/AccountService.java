package com.example.anapo.user.application.account.service;

import com.example.anapo.user.DataNotFoundException;
import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.exception.DuplicateUserIdException;
import com.example.anapo.user.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service("userReservationService")
@RequiredArgsConstructor
public class AccountService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final AccountRepository accountRepository;

    public Account join(AccountDto accountDto){

        // 아이디 중복 검사
        if (accountRepository.findByUserId(accountDto.getUserId()).isPresent()) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다.");
        }

        // 비밀번호 & 비번확인 검사
        if (!accountDto.getUserPassword().equals(accountDto.getUserPassword2())) {
            throw new PasswordMismatchException("비밀번호가 서로 일치하지 않습니다.");
        }

        // 회원 생성 및 저장
        Account account = new Account(
                accountDto.getUserPassword(),
                accountDto.getUserName(),
                accountDto.getUserId(),
                accountDto.getUserNumber(),
                accountDto.getBirth(),
                accountDto.getSex()
        );

        return accountRepository.save(account);
    }

    // 사용자 조회
    public Account getUser(String userName) {
        Optional<Account> user = accountRepository.findByUserName(userName);
        if (user.isEmpty()) {
            throw new DataNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user.orElse(null);
    }

    // 로그인
    public Account login(String userId, String userPassword) {

        Optional<Account> user = accountRepository.findByUserId(userId);

        if (user.isPresent()) {
            Account found = user.get();

            // 암호화 비밀번호 비교
            if (encoder.matches(userPassword, found.getUserPassword())) {
                return found;
            }
        }

        return null;
    }

    // 아이디 중복 방지
    public boolean existsByUserId(String userId) {
        return accountRepository.existsByUserId(userId);
    }
}