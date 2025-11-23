package com.example.anapo.user.domain.clinic.repository;

import com.example.anapo.user.domain.clinic.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    // 회원별 진료 내역 조회
    List<Clinic> findByUserId(Long acc);

    // 병원별 진료 내역 조회
    List<Clinic> findByHospitalId(Long hos);
}
