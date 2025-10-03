package com.example.anapo.user.domain.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private @NotEmpty(message = "전화번호는 필수항목입니다.")
    @Pattern(regexp = "\\d{10,11}", message = "전화번호 형식은 01012345678이어야 합니다.") String userNumber;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String sex;

    public Account(String userPassword, String userName, String userId, @NotEmpty(message = "전화번호는 필수항목입니다.") @Pattern(regexp = "\\d{10,11}", message = "전화번호 형식은 01012345678이어야 합니다.") String userNumber, String birth, String sex) {
        this.userPassword = userPassword;
        this.userName = userName;
        this.userId = userId;
        this.userNumber = userNumber;
        this.birth = birth;
        this.sex = sex;
    }
}
