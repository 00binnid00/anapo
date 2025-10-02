package com.example.anapo.user.application.hospital.service;

import com.example.anapo.user.application.hospital.dto.HospitalDto;
import com.example.anapo.user.domain.hospital.entity.Hospital;
import com.example.anapo.user.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public Hospital join(HospitalDto hospitalDto){
        Hospital hospital = new Hospital(hospitalDto.getHosName(), hospitalDto.getHosAdress(), hospitalDto.getHosNumber(), hospitalDto.getHosTime());
        return hospitalRepository.save(hospital);
    }
}
