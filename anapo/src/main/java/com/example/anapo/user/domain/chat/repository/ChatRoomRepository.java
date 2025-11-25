package com.example.anapo.user.domain.chat.repository;

import com.example.anapo.user.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByUserIdAndHospitalId(Long userId, Long hospitalId);

    List<ChatRoom> findAllByUserId(Long userId);

    List<ChatRoom> findAllByHospitalId(Long hospitalId);
}