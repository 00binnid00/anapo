package com.example.anapo.user.application.department.service;

import com.example.anapo.user.application.department.dto.DepartmentDto;
import com.example.anapo.user.domain.department.entity.Department;
import com.example.anapo.user.domain.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // 전체 진료과 조회
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(d -> new DepartmentDto(d.getId(), d.getDeptName()))
                .collect(Collectors.toList());
    }

    // 특정 진료과 조회
    public DepartmentDto getDepartment(Long id) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("진료과를 찾을 수 없습니다."));
        return new DepartmentDto(d.getId(), d.getDeptName());
    }

    // 진료과 추가 (옵션)
    public DepartmentDto createDepartment(String name) {
        Department d = new Department(name);
        Department saved = departmentRepository.save(d);
        return new DepartmentDto(saved.getId(), saved.getDeptName());
    }
}