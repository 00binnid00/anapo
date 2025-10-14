package com.example.anapo.admin.application.reservations.service;

import com.example.anapo.user.domain.reservation.entity.Reservation;
import com.example.anapo.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

        private final ReservationRepository reservationRepository;


        // 전체 예약 목록 조회 (관리자용)
        // @return Reservation 엔티티 리스트
        public List<Reservation> getAllReservations() {
            return reservationRepository.findAll();
        }
}
