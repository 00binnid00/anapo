package com.example.anapo.user.domain.hospital.repository;

import com.example.anapo.user.domain.hospital.entity.HospitalDepartment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    List<HospitalDepartment> findByHospitalId(Long hospitalId);
    boolean existsByHospital_IdAndDepartment_DeptName(Long hospitalId, String deptName);

    @Modifying
    @Transactional
    @Query("DELETE FROM HospitalDepartment hd WHERE hd.hospital.id = :hosId")
    void deleteByHospitalId(Long hosId);
}