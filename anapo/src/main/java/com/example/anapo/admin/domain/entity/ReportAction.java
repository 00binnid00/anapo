package com.example.anapo.admin.domain.entity;

import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.report.entity.Report;
import com.example.anapo.user.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class ReportAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;        // 어떤 신고를 처리한 것인지 연결

    @Enumerated(EnumType.STRING)
    private ReportStatus status;  // COMPLETED, REJECTED 등

    private String adminMemo;     // 처리 내용

    @ManyToOne
    private Account admin;        // 처리한 관리자 계정

    private LocalDateTime processedAt;
}
