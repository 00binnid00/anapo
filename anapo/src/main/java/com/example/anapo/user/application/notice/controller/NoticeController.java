package com.example.anapo.user.application.notice.controller;

import com.example.anapo.user.application.notice.dto.NoticeRequestDto;
import com.example.anapo.user.application.notice.dto.NoticeResponseDto;
import com.example.anapo.user.application.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지 생성
    @PostMapping
    public ResponseEntity<NoticeResponseDto> create(@RequestBody NoticeRequestDto dto) {
        return ResponseEntity.ok(noticeService.createNotice(dto));
    }

    // 공지 상세조회
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.getNotice(id));
    }

    // 전체 공지 조회
    @GetMapping
    public ResponseEntity<List<NoticeResponseDto>> getAll() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    // 병원별 공지 조회
    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<NoticeResponseDto>> getByHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(noticeService.getNoticesByHospitalId(hospitalId));
    }

    // 공지 수정
    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponseDto> update(
            @PathVariable Long id,
            @RequestBody NoticeRequestDto dto) {

        return ResponseEntity.ok(noticeService.updateNotice(id, dto));
    }

    // 공지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok("삭제 완료!");
    }

    /* ========================================================================================= */

    // 제목 검색
    @GetMapping("/search/title")
    public ResponseEntity<List<NoticeResponseDto>> searchByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(noticeService.searchByTitle(keyword));
    }

    // 작성자 검색
    @GetMapping("/search/writer")
    public ResponseEntity<List<NoticeResponseDto>> searchByWriter(@RequestParam String writer) {
        return ResponseEntity.ok(noticeService.searchByWriter(writer));
    }

    // 병원 + 제목 검색
    @GetMapping("/search/hospital")
    public ResponseEntity<List<NoticeResponseDto>> searchByHospitalAndTitle(
            @RequestParam Long hospitalId,
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(noticeService.searchByHospitalAndTitle(hospitalId, keyword));
    }
}
