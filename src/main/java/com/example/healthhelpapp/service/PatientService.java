package com.example.healthhelpapp.service;

import com.example.healthhelpapp.dto.request.PatientRequestDto;
import com.example.healthhelpapp.dto.response.PatientResponseDto;

import java.util.List;

public interface PatientService {
    List<PatientResponseDto> getListOfPatient();

    PatientResponseDto getById(Integer patientId);

    PatientResponseDto create(PatientRequestDto patientRequestDto);
}
