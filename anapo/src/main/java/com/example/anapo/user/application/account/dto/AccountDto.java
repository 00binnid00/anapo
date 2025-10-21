package com.example.anapo.user.application.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter

public class AccountDto {
    @NotEmpty(message = "비밀번호은 필수항목입니다.")
    private String userPassword;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String userPassword2;

    @Size(min = 3, max = 25, message = "아이디는 3자 이상 25자 이하로 입력해주세요.")
    @NotEmpty(message = "아이디는 필수항목입니다.")
    private String userId;

    @NotEmpty(message = "전화번호는 필수항목입니다.")
    @Pattern(regexp = "\\d{10,11}", message = "전화번호 형식은 01012345678이어야 합니다.")
    private String userNumber;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String userName;

    @NotEmpty(message = "성별은 필수항목입니다.")
    private String sex;

    // 생년월일을 String으로 받아올 때, 예를 들어 'yyyy-MM-dd' 형태로 받음
    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String birth;

    // 생년월일을 LocalDate로 반환하는 메서드 추가
    public LocalDate getBirthdateAsLocalDate() {
        if (birth != null && !birth.isEmpty()) {
            try {
                return LocalDate.parse(birth);  // 'yyyy-MM-dd' 형식으로 변환 시도
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("생년월일을 올바른 형식으로 입력해주세요.");
            }
        }
        return null;  // 생년월일이 비어있으면 null 반환
    }

}