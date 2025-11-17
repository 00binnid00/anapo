package com.example.anapo.user.domain.notice.repository;

import com.example.anapo.user.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 병원별 조회
    List<Notice> findByHospitalId(Long hospitalId);

    // 제목 검색 + 최신순
    List<Notice> findByTitleContainingOrderByCreatedAtDesc(String keyword);

    // 작성자 검색 + 최신순
    List<Notice> findByWriterContainingOrderByCreatedAtDesc(String writer);

    // 병원 + 제목 검색 + 최신순
    List<Notice> findByHospitalIdAndTitleContainingOrderByCreatedAtDesc(Long hospitalId, String keyword);
}

