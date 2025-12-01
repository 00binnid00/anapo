package com.example.anapo.user.application.community.controller;

import com.example.anapo.user.application.community.dto.CommentRequestDto;
import com.example.anapo.user.application.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{communityId}/comments")
    public ResponseEntity<?> createComment(
            @PathVariable Long communityId,
            @RequestBody CommentRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.create(communityId, dto));
    }

    // 댓글 조회
    @GetMapping("/{communityId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long communityId) {
        return ResponseEntity.ok(commentService.getComments(communityId));
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.update(commentId, dto));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok("댓글 삭제 완료");
    }
}
