package com.example.anapo.admin.domain.repository;

import com.example.anapo.admin.domain.entity.ReportAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportActionRepository extends JpaRepository<ReportAction, Long> {

    Optional<ReportAction> findByReportId(Long reportId); // 관리자가 처리했는지 확인용
}
