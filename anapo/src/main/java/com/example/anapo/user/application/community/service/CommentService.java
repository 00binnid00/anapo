package com.example.anapo.user.application.community.service;

import com.example.anapo.user.application.community.dto.CommentRequestDto;
import com.example.anapo.user.application.community.dto.CommentResponseDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.community.entity.Comment;
import com.example.anapo.user.domain.community.entity.Community;
import com.example.anapo.user.domain.community.repository.CommentRepository;
import com.example.anapo.user.domain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final AccountRepository accountRepository;

    // 댓글 등록
    public CommentResponseDto create(Long communityId, CommentRequestDto dto) {

        Community post = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Account account = null;
        if (dto.getAccId() != null) {
            account = accountRepository.findById(dto.getAccId()).orElse(null);
        }

        Comment parent = null;
        if (dto.getParentId() != null) {
            parent = commentRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(dto.getWriter())
                .account(account)
                .community(post)
                .parent(parent)
                .build();

        Comment saved = commentRepository.save(comment);
        return convertToDto(saved);
    }


    // 게시글 기준 댓글 조회
    public List<CommentResponseDto> getComments(Long communityId) {

        Community post = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 전체 댓글 조회
        List<Comment> comments = commentRepository.findByCommunity(post);

        // id 매핑용 map
        Map<Long, CommentResponseDto> map = new HashMap<>();

        // 먼저 DTO로 변환하며 map에 저장
        for (Comment c : comments) {
            map.put(c.getId(), convertToDtoWithoutChildren(c));
        }

        // root 댓글 담을 리스트
        List<CommentResponseDto> rootList = new ArrayList<>();

        // 부모-자식 관계 구성
        for (Comment c : comments) {
            CommentResponseDto dto = map.get(c.getId());

            if (c.getParent() == null) {
                // 부모 없는 경우 → 최상위 댓글
                rootList.add(dto);
            } else {
                // 부모 있는 경우 → 해당 부모의 children에 추가
                CommentResponseDto parentDto = map.get(c.getParent().getId());
                parentDto.getChildren().add(dto);
            }
        }

        return rootList;
    }

    private CommentResponseDto convertToDtoWithoutChildren(Comment c) {
        return CommentResponseDto.builder()
                .id(c.getId())
                .communityId(c.getCommunity().getId())
                .content(c.getContent())
                .writer(c.getWriter())
                .createdAt(c.getCreatedAt().toString())
                .updatedAt(c.getUpdatedAt().toString())
                .children(new ArrayList<>())   // children 초기화
                .build();
    }

    // 댓글 수정
    public CommentResponseDto update(Long commentId, CommentRequestDto dto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        comment = Comment.builder()
                .id(comment.getId())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .account(comment.getAccount())
                .community(comment.getCommunity())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();

        Comment updated = commentRepository.save(comment);

        return convertToDto(updated);
    }

    // 댓글 삭제
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // DTO 변환
    private CommentResponseDto convertToDto(Comment c) {
        return CommentResponseDto.builder()
                .id(c.getId())
                .communityId(c.getCommunity().getId())
                .content(c.getContent())
                .writer(c.getWriter())
                .createdAt(c.getCreatedAt().toString())
                .updatedAt(c.getUpdatedAt().toString())
                .build();
    }
}
