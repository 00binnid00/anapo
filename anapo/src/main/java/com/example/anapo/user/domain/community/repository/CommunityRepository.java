package com.example.anapo.user.domain.community.repository;

import com.example.anapo.user.domain.community.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    // 제목 검색
    Page<Community> findByTitleContaining(String keyword, Pageable pageable);

    // 작성자 검색
    Page<Community> findByWriterContaining(String writer, Pageable pageable);
}
