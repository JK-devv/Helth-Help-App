package com.example.healthhelpapp.dto.mapper.impl;

import com.example.healthhelpapp.dto.Patient;
import com.example.healthhelpapp.dto.mapper.DtoMapper;
import com.example.healthhelpapp.dto.mapper.ResponseMapper;
import com.example.healthhelpapp.dto.request.PatientRequestDto;
import com.example.healthhelpapp.dto.response.PatientResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper implements
        ResponseMapper<PatientResponseDto, Patient>,
        DtoMapper<Patient, PatientRequestDto> {

    public PatientResponseDto mapToResponse(Patient patient) {
        return PatientResponseDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .phone(patient.getPhone())
                .build();
    }

    public Patient mapToDto(PatientRequestDto patientRequestDto) {
        return Patient.builder()
                .phone(patientRequestDto.getPhone())
                .name(patientRequestDto.getName())
                .build();
    }
}

