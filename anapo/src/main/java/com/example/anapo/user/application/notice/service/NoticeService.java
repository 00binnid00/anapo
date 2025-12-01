package com.example.anapo.user.application.notice.service;


import com.example.anapo.user.application.notice.dto.NoticeRequestDto;
import com.example.anapo.user.application.notice.dto.NoticeResponseDto;
import com.example.anapo.user.domain.notice.entity.Notice;
import com.example.anapo.user.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지 생성
    public NoticeResponseDto createNotice(NoticeRequestDto dto) {
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .hospitalId(dto.getHospitalId())
                .build();

        Notice saved = noticeRepository.save(notice);
        return toDto(saved);
    }

    // 공지 상세 조회 + 조회수 증가
    public NoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

        // 조회수 증가
        notice.setViewCount(notice.getViewCount() + 1);
        Notice updated = noticeRepository.save(notice);

        return toDto(updated);
    }

    // 전체 공지 조회
    public List<NoticeResponseDto> getAllNotices() {
        return noticeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 병원별 공지 조회
    public List<NoticeResponseDto> getNoticesByHospitalId(Long hospitalId) {
        return noticeRepository.findByHospitalId(hospitalId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 공지 수정
    public NoticeResponseDto updateNotice(Long id, NoticeRequestDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setWriter(dto.getWriter());
        notice.setHospitalId(dto.getHospitalId());

        Notice updated = noticeRepository.save(notice);
        return toDto(updated);
    }

    // 공지 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // Entity → DTO 변환
    private NoticeResponseDto toDto(Notice notice) {
        return NoticeResponseDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .writer(notice.getWriter())
                .hospitalId(notice.getHospitalId())
                .viewCount(notice.getViewCount())
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }

/* ===================================================================================== */

    // 제목 검색 (최신순)
    public List<NoticeResponseDto> searchByTitle(String keyword) {
        return noticeRepository.findByTitleContainingOrderByCreatedAtDesc(keyword)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 작성자 검색 (최신순)
    public List<NoticeResponseDto> searchByWriter(String writer) {
        return noticeRepository.findByWriterContainingOrderByCreatedAtDesc(writer)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 병원 + 제목 검색 (최신순)
    public List<NoticeResponseDto> searchByHospitalAndTitle(Long hospitalId, String keyword) {
        return noticeRepository.findByHospitalIdAndTitleContainingOrderByCreatedAtDesc(hospitalId, keyword)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

/* ===================================================================================== */

    // 전체 공지 페이징
    public Page<NoticeResponseDto> getPagedNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return noticeRepository.findAll(pageable)
                .map(this::toDto);
    }

    // 병원별 공지 페이징
    public Page<NoticeResponseDto> getPagedNoticesByHospital(Long hospitalId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return noticeRepository.findByHospitalId(hospitalId, pageable)
                .map(this::toDto);
    }

    // 제목 검색 + 페이징
    public Page<NoticeResponseDto> searchByTitlePaged(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return noticeRepository.findByTitleContaining(keyword, pageable)
                .map(this::toDto);
    }

    // 작성자 검색 + 페이징
    public Page<NoticeResponseDto> searchByWriterPaged(String writer, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return noticeRepository.findByWriterContaining(writer, pageable)
                .map(this::toDto);
    }

    // 병원 + 제목 검색 + 페이징
    public Page<NoticeResponseDto> searchByHospitalAndTitlePaged(Long hospitalId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return noticeRepository.findByHospitalIdAndTitleContaining(hospitalId, keyword, pageable)
                .map(this::toDto);
    }
}
