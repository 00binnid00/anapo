package com.example.anapo.admin.application.service;

import com.example.anapo.admin.application.dto.ReportProcessDto;
import com.example.anapo.admin.domain.entity.ReportAction;
import com.example.anapo.admin.domain.repository.ReportActionRepository;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.report.entity.Report;
import com.example.anapo.user.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportActionService {

    private final ReportRepository reportRepository;
    private final ReportActionRepository reportActionRepository;
    private final AccountRepository accountRepository;

    public ReportAction process(Long reportId, ReportProcessDto dto) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고 없음"));

        Account admin = accountRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new IllegalArgumentException("관리자 없음"));

        ReportAction action = ReportAction.builder()
                .report(report)
                .status(dto.getStatus())
                .adminMemo(dto.getAdminMemo())
                .admin(admin)
                .build();

        return reportActionRepository.save(action);
    }
}
