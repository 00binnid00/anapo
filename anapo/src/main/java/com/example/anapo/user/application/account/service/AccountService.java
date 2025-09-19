package com.example.anapo.user.application.account.service;

import com.example.anapo.user.application.account.dto.AccountDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account join(AccountDto accountDto){
        Account account = new Account(accountDto.getUserPassword(),accountDto.getName(), accountDto.getUserId(), accountDto.getUserNumber(), accountDto.getBirth(), accountDto.getSex());
        return accountRepository.save(account);
    }
}
