package com.example.anapo.admin.application.reservations.controller;

import com.example.anapo.admin.application.reservations.service.ReservationService;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    // 관리자용 전체 예약 조회
    @GetMapping("/admin")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
}
