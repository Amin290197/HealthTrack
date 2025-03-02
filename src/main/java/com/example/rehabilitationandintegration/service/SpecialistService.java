package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.CertificateDto;
import com.example.rehabilitationandintegration.model.request.SpecialistCreateUpdateDto;
import com.example.rehabilitationandintegration.model.request.SpecialistUpdateDto;
import com.example.rehabilitationandintegration.model.response.PatientResponseDto;
import com.example.rehabilitationandintegration.model.response.SpecialistAllResponseDto;
import com.example.rehabilitationandintegration.model.response.SpecialistResponseDto;
import com.example.rehabilitationandintegration.model.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecialistService {
    Page<SpecialistAllResponseDto> all(Pageable pageable);

    SpecialistResponseDto get(Long id);

    Page<SpecialistAllResponseDto> getSpecialty(Pageable pageable, Long specialtyId);

    Page<UserResponseDto> getSpecialistPatients(Pageable pageable, Long id);

    void create(SpecialistCreateUpdateDto specialistDto);

    void update(Long id, SpecialistCreateUpdateDto specialistDto);

    void delete(Long id);

    void changeStatus(Long id, Status status);

}
