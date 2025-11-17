package com.example.anapo.user.application.account.service;

import com.example.anapo.user.DataNotFoundException;
import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service("userReservationService")
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // DB 저장 트랜잭션 적용
    @Transactional
    public  Account create(AccountDto accountDto) {
        Account account = Account.builder()
                .userId(accountDto.getUserId())
                .userPassword(encoder.encode(accountDto.getUserPassword())) // 비밀번호 암호화
                .userName(accountDto.getUserName())
                .userNumber(accountDto.getUserNumber())
                .birth(accountDto.getBirth())
                .sex(accountDto.getSex())
                .build();

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