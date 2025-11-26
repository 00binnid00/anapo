package com.example.anapo.user.application.bookmark.controller;

import com.example.anapo.user.application.bookmark.dto.BookmarkCreateDto;
import com.example.anapo.user.application.bookmark.dto.BookmarkResponseDto;
import com.example.anapo.user.application.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 즐겨찾기 등록
    @PostMapping
    public ResponseEntity<BookmarkResponseDto> addBookmark(@RequestBody BookmarkCreateDto dto) {
        return ResponseEntity.ok(bookmarkService.addBookmark(dto));
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{userId}/{hospitalId}")
    public ResponseEntity<?> removeBookmark(
            @PathVariable Long userId,
            @PathVariable Long hospitalId
    ) {
        bookmarkService.removeBookmark(userId, hospitalId);
        return ResponseEntity.ok("즐겨찾기에서 삭제되었습니다.");
    }

    // 즐겨찾기 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<BookmarkResponseDto>> getBookmarks(@PathVariable Long userId) {
        return ResponseEntity.ok(bookmarkService.getBookmarks(userId));
    }
}
