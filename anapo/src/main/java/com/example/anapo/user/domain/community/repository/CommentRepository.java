package com.example.anapo.user.domain.community.repository;

import com.example.anapo.user.domain.community.entity.Comment;
import com.example.anapo.user.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCommunityOrderByCreatedAtAsc(Community community);
}
