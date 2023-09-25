package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Patient;
import com.example.healthhelpapp.dto.mapper.DtoMapper;
import com.example.healthhelpapp.dto.mapper.ResponseMapper;
import com.example.healthhelpapp.dto.request.PatientRequestDto;
import com.example.healthhelpapp.dto.response.PatientResponseDto;
import com.example.healthhelpapp.repository.PatientRepository;
import com.example.healthhelpapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ResponseMapper<PatientResponseDto, Patient> responseMapper;
    private final DtoMapper<Patient, PatientRequestDto> dtoMapper;

    public List<PatientResponseDto> getListOfPatient() {
        return patientRepository.findAll()
                .stream()
                .map(responseMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public PatientResponseDto getById(Integer patientId) {
        return responseMapper.mapToResponse(patientRepository.findById(patientId)
                .orElseThrow(
                        () -> new RuntimeException(
                                String.format("There is no patient by id: %s", patientId))));
    }

    @Override
    public PatientResponseDto create(PatientRequestDto patientRequestDto) {
        return responseMapper.mapToResponse(
                patientRepository.save(
                        dtoMapper.mapToDto(patientRequestDto)));
    }
}
