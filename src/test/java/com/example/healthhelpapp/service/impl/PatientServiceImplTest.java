package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Patient;
import com.example.healthhelpapp.dto.mapper.DtoMapper;
import com.example.healthhelpapp.dto.mapper.ResponseMapper;
import com.example.healthhelpapp.dto.request.PatientRequestDto;
import com.example.healthhelpapp.dto.response.PatientResponseDto;
import com.example.healthhelpapp.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.example.healthhelpapp.TestConstant.TEST_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {PatientServiceImpl.class})
class PatientServiceImplTest {
    @Autowired
    private PatientServiceImpl patientService;
    @MockBean
    private ResponseMapper<PatientResponseDto, Patient> responseMapper;
    @MockBean
    private DtoMapper<Patient, PatientRequestDto> dtoMapper;
    @MockBean
    private PatientRepository patientRepository;

    @Test
    void shouldGetListOfPatient() {

        Patient patient = Patient.builder()
                .name(TEST_NAME)
                .build();
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .name(TEST_NAME)
                .id(1)
                .build();

        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(responseMapper.mapToResponse(patient)).thenReturn(patientResponseDto);

        List<PatientResponseDto> actual = patientService.getListOfPatient();
        assertEquals(1, actual.size());
        assertEquals(TEST_NAME, actual.get(0).getName());

        verify(responseMapper, times(1)).mapToResponse(patient);
        verify(patientRepository, times(1)).findAll();

    }

    @Test
    void shouldReturnPatientById() {
        Patient patient = Patient.builder()
                .id(1)
                .name(TEST_NAME)
                .build();
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .name(TEST_NAME)
                .id(1)
                .build();

        when(patientRepository.findById(patient.getId()))
                .thenReturn(Optional.of(patient));
        when(responseMapper.mapToResponse(patient)).thenReturn(patientResponseDto);
        PatientResponseDto actual = patientService.getById(1);
        assertEquals(patient.getName(), actual.getName());

        verify(responseMapper, times(1)).mapToResponse(patient);
        verify(patientRepository, times(1)).findById(patient.getId());
    }

    @Test
    void shouldNotReturnPatientByNotExistedId() {

        when(patientRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                patientService.getById(1),
                String.format("There is no patient by id: %s", 1));

        verify(patientRepository, times(1)).findById(1);
    }

    @Test
    void shouldCreatePatient() {
        PatientRequestDto requestDto = PatientRequestDto.builder()
                .name(TEST_NAME)
                .build();
        Patient patient = Patient.builder()
                .name(TEST_NAME)
                .build();
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .name(TEST_NAME)
                .id(1)
                .build();

        when(patientRepository.save(any()))
                .thenReturn(patient);
        when(responseMapper.mapToResponse(patient)).thenReturn(patientResponseDto);

        PatientResponseDto actual = patientService.create(requestDto);
        assertEquals(requestDto.getName(), actual.getName());

        verify(responseMapper, times(1)).mapToResponse(patient);
        verify(patientRepository,times(1)).save(any());
    }
}