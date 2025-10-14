package com.example.anapo.user.application.reservation.controller;

import com.example.anapo.user.application.reservation.dto.ReservationDto;
import com.example.anapo.user.application.reservation.service.ReservationService;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 등록
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationDto dto) {
        Reservation saved = reservationService.createReservation(dto);
        return ResponseEntity.ok("예약이 성공적으로 등록되었습니다. 예약번호: " + saved.getId());
    }
}
