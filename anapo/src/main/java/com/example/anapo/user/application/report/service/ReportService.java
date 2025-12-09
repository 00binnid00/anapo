package com.example.anapo.user.application.report.service;

import com.example.anapo.user.application.report.dto.ReportCreateDto;
import com.example.anapo.admin.application.dto.ReportProcessDto;
import com.example.anapo.user.application.report.dto.ReportResponseDto;
import com.example.anapo.user.domain.report.entity.Report;
import com.example.anapo.user.enums.ReportStatus;
import com.example.anapo.user.domain.report.repository.ReportRepository;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AccountRepository accountRepository;

    public Report createReport(ReportCreateDto dto) {

        Account reporter = accountRepository.findById(dto.getReporterId())
                .orElseThrow(() -> new IllegalArgumentException("신고자 없음"));

        Account reported = dto.getReportedId() != null
                ? accountRepository.findById(dto.getReportedId())
                .orElseThrow(() -> new IllegalArgumentException("피신고자 없음"))
                : null;

        Report report = Report.builder()
                .reporter(reporter)
                .reported(reported)
                .reportType(dto.getReportType())
                .targetType(dto.getTargetType())
                .targetId(dto.getTargetId())
                .description(dto.getDescription())
                .build();

        return reportRepository.save(report);
    }
}
