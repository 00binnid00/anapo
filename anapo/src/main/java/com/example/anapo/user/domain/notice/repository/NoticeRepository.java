package com.example.anapo.user.domain.notice.repository;

import com.example.anapo.user.domain.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

/* ===================================================================================== */

    // 병원별 페이징
    Page<Notice> findByHospitalId(Long hospitalId, Pageable pageable);

    // 제목 검색 + 페이징
    Page<Notice> findByTitleContaining(String keyword, Pageable pageable);

    // 작성자 검색 + 페이징
    Page<Notice> findByWriterContaining(String writer, Pageable pageable);

    // 병원 + 제목 검색 + 페이징
    Page<Notice> findByHospitalIdAndTitleContaining(Long hospitalId, String keyword, Pageable pageable);
}

