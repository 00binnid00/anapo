package com.example.anapo.user.application.department.controller;

import com.example.anapo.user.application.department.dto.DepartmentDto;
import com.example.anapo.user.application.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 전체 진료과 조회
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // 진료과 하나 조회
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartment(id));
    }

    // 진료과 등록 (테스트용)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, String> request) {
        String name = request.get("deptName");
        DepartmentDto saved = departmentService.createDepartment(name);

        return ResponseEntity.ok(
                Map.of(
                        "message", "진료과 등록 완료",
                        "id", saved.getId(),
                        "name", saved.getDeptName()
                )
        );
    }
}