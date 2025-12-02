package com.example.anapo.admin.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStatusUpdateDto {

    // ex: "ACTIVE", "SUSPENDED", "DELETED" 이런 값 넘겨줄 용도
    // ACTIVE = 활성화 / SUSPENDED = 비활성화(정지) / DELETED = 삭제(탈퇴)
    private String status;
}
