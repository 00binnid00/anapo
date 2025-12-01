package com.example.anapo.user.application.community.controller;

import com.example.anapo.user.application.community.dto.CommunityRequestDto;
import com.example.anapo.user.application.community.dto.CommunityResponseDto;
import com.example.anapo.user.application.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CommunityRequestDto dto) {
        return ResponseEntity.ok(communityService.create(dto));
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(communityService.getAll());
    }

    // 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return ResponseEntity.ok(communityService.get(id));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody CommunityRequestDto dto
    ) {
        return ResponseEntity.ok(communityService.update(id, dto));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        communityService.delete(id);
        return ResponseEntity.ok("삭제 완료!");
    }

    // 제목 검색 + 페이징
    @GetMapping("/search/title")
    public ResponseEntity<?> searchByTitle(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(communityService.searchByTitle(keyword, page, size));
    }

    // 작성자 검색 + 페이징
    @GetMapping("/search/writer")
    public ResponseEntity<?> searchByWriter(
            @RequestParam String writer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(communityService.searchByWriter(writer, page, size));
    }
}
