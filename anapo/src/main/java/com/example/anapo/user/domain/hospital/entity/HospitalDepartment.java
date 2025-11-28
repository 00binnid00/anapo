package com.example.anapo.user.domain.hospital.entity;

import com.example.anapo.user.domain.department.entity.Department;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class HospitalDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public HospitalDepartment(Hospital hospital, Department department) {
        this.hospital = hospital;
        this.department = department;
    }
}