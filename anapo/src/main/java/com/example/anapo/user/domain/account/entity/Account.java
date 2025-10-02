package com.example.anapo.user.domain.account.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private Integer userNumber;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String sex;

    public Account(String userPassword, String userName, String userId, Integer userNumber, String birth, String sex) {
        this.userPassword = userPassword;
        this.userName = userName;
        this.userId = userId;
        this.userNumber = userNumber;
        this.birth = birth;
        this.sex = sex;
    }
}
