package com.example.anapo.admin.application.dto;

import com.example.anapo.user.enums.ReportStatus;
import lombok.*;

@Getter
@Setter
public class ReportProcessDto {

    private ReportStatus status;      // COMPLETED or REJECTED 등
    private String adminMemo;         // 처리 내용 메모
    private Long adminId;             // 처리한 관리자 ID
}
