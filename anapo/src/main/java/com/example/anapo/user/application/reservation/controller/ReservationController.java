package com.example.anapo.user.application.reservation.controller;

import com.example.anapo.user.application.reservation.dto.ReservationDto;
import com.example.anapo.user.application.reservation.service.ReservationService;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/join")
    public Reservation joinUser(@RequestBody ReservationDto reservationDto){
        return ReservationService.join(reservationDto);
    }
}
