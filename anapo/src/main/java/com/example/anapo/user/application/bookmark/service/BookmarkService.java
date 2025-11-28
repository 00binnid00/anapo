package com.example.anapo.user.application.bookmark.service;

import com.example.anapo.user.application.bookmark.dto.BookmarkCreateDto;
import com.example.anapo.user.application.bookmark.dto.BookmarkResponseDto;
import com.example.anapo.user.domain.bookmark.entity.Bookmark;
import com.example.anapo.user.domain.bookmark.repository.BookmarkRepository;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import com.example.anapo.user.exception.DuplicateBookmarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final HospitalRepository hospitalRepository;

    // 즐겨찾기 등록
    public BookmarkResponseDto addBookmark(BookmarkCreateDto dto) {

        // 이미 즐겨찾기 되어있는지 확인
        bookmarkRepository.findByUserIdAndHospitalId(dto.getUserId(), dto.getHospitalId())
                .ifPresent(b -> {
                    throw new DuplicateBookmarkException("이미 즐겨찾기에 등록된 병원입니다.");
                });

        // 병원 존재 여부 확인
        Hospital hos = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("병원을 찾을 수 없습니다."));

        Bookmark bookmark = Bookmark.builder()
                .userId(dto.getUserId())
                .hospitalId(dto.getHospitalId())
                .createdAt(LocalDateTime.now())
                .build();

        Bookmark saved = bookmarkRepository.save(bookmark);

        return BookmarkResponseDto.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .hospitalId(saved.getHospitalId())
                .hosName(hos.getHosName())
                .hosAddress(hos.getHosAddress())
                .build();
    }


    // 즐겨찾기 삭제
    public void removeBookmark(Long userId, Long hospitalId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndHospitalId(userId, hospitalId)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기에 존재하지 않는 병원입니다."));

        bookmarkRepository.delete(bookmark);
    }

    // 특정 사용자의 즐겨찾기 리스트 조회
    public List<BookmarkResponseDto> getBookmarks(Long userId) {

        List<Bookmark> list = bookmarkRepository.findByUserId(userId);

        return list.stream().map(b -> {
            Hospital hos = hospitalRepository.findById(b.getHospitalId())
                    .orElseThrow(() -> new IllegalArgumentException("병원을 찾을 수 없습니다."));

            return BookmarkResponseDto.builder()
                    .id(b.getId())
                    .userId(b.getUserId())
                    .hospitalId(b.getHospitalId())
                    .hosName(hos.getHosName())
                    .hosAddress(hos.getHosAddress())
                    .createdAt(b.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());
    }

}
