package com.example.anapo.user.application.clinic.service;

import com.example.anapo.user.application.clinic.dto.ClinicDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.clinic.entity.Clinic;
import com.example.anapo.user.domain.clinic.repository.ClinicRepository;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import com.example.anapo.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final ReservationRepository reservationRepository;
    private final AccountRepository accountRepository;
    private final HospitalRepository hospitalRepository;

    // 진료 내역 등록
    public Clinic createClinic(ClinicDto dto) {
        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다."));
        Account user = accountRepository.findById(dto.getAcc())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Hospital hospital = hospitalRepository.findById(dto.getHos())
                .orElseThrow(() -> new IllegalArgumentException("병원 정보를 찾을 수 없습니다."));

        Clinic clinic = new Clinic(
                dto.getClinicDate(),
                dto.getDiagnosis(),
                dto.getTreatment(),
                dto.getDoctorName(),
                reservation,
                user,
                hospital
        );

        return clinicRepository.save(clinic);
    }

    // 회원별 진료 내역 조회
    public List<Clinic> getClinicsByUser(Long acc) {
        return clinicRepository.findByUserId(acc);
    }

    // 병원별 진료 내역 조회
    public List<Clinic> getClinicsByHospital(Long hos) {
        return clinicRepository.findByHospitalId(hos);
    }
}
