package com.example.anapo.user.application.reservation.service;

import com.example.anapo.user.application.reservation.dto.ReservationDto;
import com.example.anapo.user.domain.account.entity.Account;
import com.example.anapo.user.domain.account.repository.AccountRepository;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalDepartmentRepository;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import com.example.anapo.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final AccountRepository accountRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalDepartmentRepository hospitalDepartmentRepository;

    // 예약 등록
    public Reservation createReservation(ReservationDto dto) {
        // 1. 필수 입력값 검증
        if (dto.getReserDate() == null || dto.getDepartment() == null || dto.getAcc() == null || dto.getHos() == null) {
            throw new IllegalArgumentException("예약 정보가 부족합니다. (날짜, 진료과, 사용자, 병원 정보는 필수입니다)");
        }

        // 2. 이 병원이 해당 진료과를 제공하는지 검증
        boolean exists = hospitalDepartmentRepository.existsByHospital_IdAndDepartment_DeptName(
                dto.getHos(), dto.getDepartment()
        );

        if (!exists) {
            throw new IllegalArgumentException("해당 병원은 해당 진료과를 제공하지 않습니다.");
        }

        // 3. 동일 시간대 예약 인원 확인 (3명 이상이면 예약 불가)
        long count = reservationRepository.countReservationsAtSameTime(
                dto.getHos().longValue(),
                dto.getReserDate(),
                dto.getDepartment()
        );

        if (count >= 3) {
            throw new IllegalStateException("해당 시간대는 이미 예약이 마감되었습니다. (최대 3명까지 가능)");
        }

        // 4. Account, Hospital 엔티티 조회
        Account user = accountRepository.findById(dto.getAcc())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Hospital hospital = hospitalRepository.findById(dto.getHos())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원입니다."));

        // 5. 예약 정보 생성
        Reservation reservation = new Reservation(
                dto.getReserDate(),
                dto.getDepartment(),
                true,   // 예약 여부
                user,   // Account 엔티티
                hospital // Hospital 엔티티
        );

        return reservationRepository.save(reservation);
    }

    // 예약 수정
    public Reservation updateReservation(Long reservationId, ReservationDto dto) {

        // 1. 예약 존재 여부 확인
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다."));

        // 기존 정보
        LocalDateTime oldDate = reservation.getReserDate();
        String oldDept = reservation.getDepartment();
        Long hosId = reservation.getHospital().getId();

        // 2. 변경 요청된 값 적용 (널이 아니면 업데이트)
        LocalDateTime newDate = dto.getReserDate() != null ? dto.getReserDate() : oldDate;
        String newDept = dto.getDepartment() != null ? dto.getDepartment() : oldDept;

        // 3. 동일 시간대 예약 인원 확인
        long count = reservationRepository.countReservationsAtSameTime(hosId, newDate, newDept);

        // 본인 예약 1자리는 제외하고 계산해야 함
        if (!newDate.equals(oldDate) || !newDept.equals(oldDept)) {
            if (count >= 3) {
                throw new IllegalStateException("해당 시간대는 이미 예약이 마감되었습니다. (최대 3명까지 가능)");
            }
        }

        // 4. 실제 업데이트 적용
        reservation.changeReservation(newDate, newDept);

        // 5. 저장 후 반환
        return reservationRepository.save(reservation);
    }

    // 예약 삭제
    public void deleteReservation(Long reservationId) {

        // 존재 여부 확인
        boolean exists = reservationRepository.existsById(reservationId);
        if (!exists) {
            throw new IllegalArgumentException("해당 예약이 존재하지 않습니다.");
        }

        // DB에서 완전 삭제
        reservationRepository.deleteById(reservationId);
    }
}
