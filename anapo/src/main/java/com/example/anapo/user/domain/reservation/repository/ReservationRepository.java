package com.example.anapo.user.domain.reservation.repository;

import com.example.anapo.user.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

     // 특정 병원(hos), 시간(reserDate), 진료과(department)에 이미 예약된 인원 수를 반환
     // - 한 시간대에 예약 인원이 3명 이상이면 추가 예약이 불가능하도록 제어할 때 사용
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.hos = :hos AND r.reserDate = :reserDate AND r.department = :department")
    long countReservationsAtSameTime(Long hos, Date reserDate, String department);
}
