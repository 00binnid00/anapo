package com.example.anapo.user.application.reservation.service;

import com.example.anapo.user.application.reservation.dto.ReservationDto;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import com.example.anapo.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final  ReservationRepository reservationRepository;

    public Reservation createReservation(ReservationDto dto) {
        // 1. 필수 입력값 검증
        if (dto.getReserDate() == null || dto.getDepartment() == null || dto.getAcc() == null || dto.getHos() == null) {
            throw new IllegalArgumentException("예약 정보가 부족합니다. (날짜, 진료과, 사용자, 병원 정보는 필수입니다)");
        }

        // 2. 동일 시간대 예약 인원 확인 (3명 이상이면 예약 불가)
        long count = reservationRepository.countReservationsAtSameTime(
                dto.getHos().longValue(),
                dto.getReserDate(),
                dto.getDepartment()
        );

        if (count >= 3) {
            throw new IllegalStateException("해당 시간대는 이미 예약이 마감되었습니다. (최대 3명까지 가능)");
        }

        // 3. 예약 정보 생성 및 DB 저장
        Reservation reservation = new Reservation(
                dto.getReserDate(),
                dto.getDepartment(),
                true,  // 예약 여부: true (예약됨)
                dto.getAcc(),
                dto.getHos()
        );

        return reservationRepository.save(reservation);
    }
}
