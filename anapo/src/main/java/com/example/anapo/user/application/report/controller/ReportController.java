package com.example.anapo.user.application.report.controller;

import com.example.anapo.user.application.report.dto.ReportCreateDto;
import com.example.anapo.user.application.report.dto.ReportResponseDto;
import com.example.anapo.user.application.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReportCreateDto dto) {
        return ResponseEntity.ok(reportService.createReport(dto));
    }
}
