package com.example.anapo.user.application.report.dto;

import com.example.anapo.user.enums.ReportTargetType;
import com.example.anapo.user.enums.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCreateDto {

    // 프론트에서 세션으로 가져와도 되고, 임시로 넘겨도 되고
    private Long reporterId;          // 신고자 ID
    private Long reportedId;          // 피신고자 ID (없으면 null)

    private ReportType reportType;    // 신고 유형
    private ReportTargetType targetType; // 신고 대상 타입
    private Long targetId;            // 대상 PK

    private String description;       // 신고 상세 내용
}
