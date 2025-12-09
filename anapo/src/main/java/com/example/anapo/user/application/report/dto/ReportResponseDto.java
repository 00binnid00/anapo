package com.example.anapo.user.application.report.dto;

import com.example.anapo.admin.domain.entity.ReportAction;
import com.example.anapo.user.domain.report.entity.Report;
import com.example.anapo.user.enums.ReportStatus;
import com.example.anapo.user.enums.ReportTargetType;
import com.example.anapo.user.enums.ReportType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReportResponseDto {

    private Long id;
    private Long reporterId;
    private String reporterName;
    private Long reportedId;
    private String reportedName;

    private ReportType reportType;
    private ReportTargetType targetType;
    private Long targetId;

    private String description;
    private ReportStatus status;

    private String adminMemo;
    private Long processedByAdminId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public static ReportResponseDto from(Report report) {

        // 관리자 처리 정보 ReportAction 가져오기
        ReportAction action = report.getAction();

        return ReportResponseDto.builder()
                .id(report.getId())
                .reporterId(report.getReporter() != null ? report.getReporter().getId() : null)
                .reporterName(report.getReporter() != null ? report.getReporter().getUserName() : null)
                .reportedId(report.getReported() != null ? report.getReported().getId() : null)
                .reportedName(report.getReported() != null ? report.getReported().getUserName() : null)
                .reportType(report.getReportType())
                .targetType(report.getTargetType())
                .targetId(report.getTargetId())
                .description(report.getDescription())

                // ReportAction에서 관리자 처리 상태를 가져옴
                .status(action != null ? action.getStatus() : null)
                .adminMemo(action != null ? action.getAdminMemo() : null)
                .processedByAdminId(action != null && action.getAdmin() != null ? action.getAdmin().getId() : null)
                .processedAt(action != null ? action.getProcessedAt() : null)

                .createdAt(report.getCreatedAt())
                .build();
    }
}
