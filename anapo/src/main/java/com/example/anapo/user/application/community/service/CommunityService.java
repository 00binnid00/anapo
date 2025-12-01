package com.example.anapo.user.application.community.service;

import com.example.anapo.user.application.community.dto.CommunityRequestDto;
import com.example.anapo.user.application.community.dto.CommunityResponseDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.community.entity.Community;
import com.example.anapo.user.domain.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final AccountRepository accountRepository;

    // 게시글 생성
    public CommunityResponseDto create(CommunityRequestDto dto) {

        Account writerAccount = null;

        if (dto.getAccId() != null) {
            writerAccount = accountRepository.findById(dto.getAccId())
                    .orElse(null);
        }

        Community post = Community.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .account(writerAccount)
                .build();

        Community saved = communityRepository.save(post);

        return convertToDto(saved);
    }

    // 게시글 단건 조회
    public CommunityResponseDto get(Long id) {
        Community post = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return convertToDto(post);
    }

    // 전체 게시글 (최신순)
    public List<CommunityResponseDto> getAll() {
        return communityRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 게시글 수정
    public CommunityResponseDto update(Long id, CommunityRequestDto dto) {
        Community post = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        post = Community.builder()
                .id(post.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .account(post.getAccount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();

        Community updated = communityRepository.save(post);

        return convertToDto(updated);
    }

    // 삭제
    public void delete(Long id) {
        communityRepository.deleteById(id);
    }

    // 제목 검색 + 페이징
    public Page<CommunityResponseDto> searchByTitle(String keyword, int page, int size) {
        return communityRepository.findByTitleContaining(keyword, PageRequest.of(page, size))
                .map(this::convertToDto);
    }

    // 작성자 검색 + 페이징
    public Page<CommunityResponseDto> searchByWriter(String writer, int page, int size) {
        return communityRepository.findByWriterContaining(writer, PageRequest.of(page, size))
                .map(this::convertToDto);
    }

    // DTO 변환
    private CommunityResponseDto convertToDto(Community post) {
        return CommunityResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter())
                .createdAt(post.getCreatedAt().toString())
                .updatedAt(post.getUpdatedAt().toString())
                .build();
    }
}
