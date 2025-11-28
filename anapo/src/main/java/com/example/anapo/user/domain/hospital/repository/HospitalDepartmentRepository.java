package com.example.anapo.user.domain.hospital.repository;

import com.example.anapo.user.domain.hospital.entity.HospitalDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalDepartmentRepository extends JpaRepository<HospitalDepartment, Long> {
    List<HospitalDepartment> findByHospitalId(Long hospitalId);
    boolean existsByHospital_IdAndDepartment_DeptName(Long hospitalId, String deptName);
}