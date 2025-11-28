package com.example.anapo.user.application.reservation.controller;

import com.example.anapo.user.application.reservation.dto.ReservationDto;
import com.example.anapo.user.application.reservation.service.ReservationService;
import com.example.anapo.user.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 등록
    @PostMapping("/reservations")
    public ResponseEntity<?> createReservation(@RequestBody ReservationDto dto) {
        // @RequestBody → 클라이언트가 보낸 JSON 데이터를 ReservationDto 객체로 변환
        try {
            Reservation saved = reservationService.createReservation(dto);

            // 예약이 성공적으로 등록되었을 경우, HTTP 200 OK 응답 반환
            return ResponseEntity.ok(Map.of(
                    "message", "예약이 성공적으로 등록되었습니다.", // 예약 성공 메세지
                    "reservationId", saved.getId(), // 생성된 예약 ID
                    "hospitalId", saved.getHospital().getId(), // 병원 ID
                    "department", saved.getDepartment(), // 진료 과목
                    "reserDate", saved.getReserDate() // 예약 날짜
            ));
        } catch (IllegalArgumentException e) {
            // 필수 정보 누락 등
            // 사용자가 필수 값을 안 넣거나 형식이 잘못됐을 경우
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (IllegalStateException e) {
            // 예약 인원 초과 등
            // 비지니스 로직상 예약이 불가능 할 때
            return ResponseEntity.status(409).body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            // 기타 예외
            // 예기치 못한 서버 오류
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "서버 오류가 발생했습니다.",
                    "details", e.getMessage()
            ));
        }
    }

    // 예약 수정
    @PatchMapping("/reservations/{id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable Long id,
            @RequestBody ReservationDto dto
    ) {
        try {
            Reservation updated = reservationService.updateReservation(id, dto);

            return ResponseEntity.ok(Map.of(
                    "message", "예약이 성공적으로 변경되었습니다.",
                    "reservationId", updated.getId(),
                    "newReserDate", updated.getReserDate(),
                    "newDepartment", updated.getDepartment()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "서버 오류가 발생했습니다.",
                    "details", e.getMessage()
            ));
        }
    }

    // 예약 삭제
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);

            return ResponseEntity.ok(Map.of(
                    "message", "예약이 정상적으로 삭제되었습니다.",
                    "reservationId", id
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
