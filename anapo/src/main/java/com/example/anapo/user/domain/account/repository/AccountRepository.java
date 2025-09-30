package com.example.anapo.user.domain.account.repository;

import com.example.anapo.user.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.validation.constraints.Size;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository <Account,Long> {
    static Optional<Account> findByUsername(String userName); // 반환 타입을 Optional로 변경
    static boolean existsByUsername(@Size(min = 3, max = 25, message = "아이디는 3자 이상 25자 이하로 입력해주세요.") @NotEmpty(message = "아이디는 필수항목입니다.") String username);
    void deleteByUsername(String username);

}