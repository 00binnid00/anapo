package com.example.anapo.user.application.hospital.controller;

import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.application.hospital.service.HospitalService;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/join")
    public Hospital joinUser(@RequestBody HospitalDto hospitalDto){
        return hospitalService.join(hospitalDto);

    }
}
