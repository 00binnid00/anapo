package com.example.anapo.user.domain.community.repository;

import com.example.anapo.user.domain.community.entity.Comment;
import com.example.anapo.user.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.community = :community ORDER BY c.createdAt ASC")
    List<Comment> findByCommunity(Community community);
}
