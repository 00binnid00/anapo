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

    public Reservation join(ReservationDto reservationDto){
        Reservation reservation = new Reservation(reservationDto.getReserDate(), reservationDto.getReserYesNo(), reservationDto.getClinicYesNo(), reservationDto.getAcc(), reservationDto.getHos());
        return reservationRepository.save(reservation);
    }
}
