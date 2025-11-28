package com.example.anapo.user.domain.department.repository;

import com.example.anapo.user.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByOrderByIdAsc();
}