package com.example.anapo.admin.domain.repository;

import com.example.anapo.user.domain.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Hospital, Long> {

    // Query를 쓴 이유 : 기본 메서드로만 통계, 집계 처리가 어려움
    // 예약 많은 병원
    // Hospital과 Reservation을 LEFT JOIN → 병원별 예약 COUNT → 큰 순으로 정렬
    @Query("SELECT h.id, h.hosName, COUNT(r) " +
            "FROM Hospital h LEFT JOIN Reservation r ON h.id = r.hospital.id " +
            "GROUP BY h.id ORDER BY COUNT(r) DESC")
    List<Object[]> findHospitalsByReservationCount();

    // 즐겨찾기 많은 병원
    // Hospital과 Bookmark LEFT JOIN → 병원별 즐겨찾기 COUNT → 큰 순으로 정렬
    @Query("SELECT h.id, h.hosName, COUNT(b) " +
            "FROM Hospital h LEFT JOIN Bookmark b ON h.id = b.hospital.id " +
            "GROUP BY h.id ORDER BY COUNT(b) DESC")
    List<Object[]> findHospitalsByBookmarkCount();

    // 재방문율 높은
    // 동일 병원에서 같은 사용자가 2번 이상 방문한 경우 revisitor로 카운트
    // totalUsers = 해당 병원 이용한 전체 사용자 수
    @Query("SELECT h.id, h.hosName, " +
            "SUM(CASE WHEN COUNT(r) > 1 THEN 1 ELSE 0 END) AS revisitors, " +
            "COUNT(DISTINCT r.account.id) AS totalUsers " +
            "FROM Hospital h LEFT JOIN Reservation r ON h.id = r.hospital.id " +
            "GROUP BY h.id, h.hosName " +
            "ORDER BY revisitors DESC")
    List<Object[]> findHospitalsByRevisitRate();
}
