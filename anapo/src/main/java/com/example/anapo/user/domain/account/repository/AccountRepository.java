package com.example.anapo.user.domain.account.repository;

import com.example.anapo.user.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserName(String userName);
    Optional<Account> findByUserId(String userId);
    boolean existsByUserName(String userName);
}
