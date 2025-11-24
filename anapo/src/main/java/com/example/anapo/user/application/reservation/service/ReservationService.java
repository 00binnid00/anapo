package com.example.anapo.user.application.reservation.service;

import com.example.anapo.user.application.reservation.dto.ReservationDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import com.example.anapo.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AccountRepository accountRepository;
    private final HospitalRepository hospitalRepository;

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

        // 3. Account, Hospital 엔티티 조회
        Account user = accountRepository.findById(dto.getAcc())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Hospital hospital = hospitalRepository.findById(dto.getHos())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원입니다."));

        // 4. 예약 정보 생성
        Reservation reservation = new Reservation(
                dto.getReserDate(),
                dto.getDepartment(),
                true,   // 예약 여부
                user,   // Account 엔티티
                hospital // Hospital 엔티티
        );

        return reservationRepository.save(reservation);
    }
}
