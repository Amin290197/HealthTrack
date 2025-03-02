package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.TherapyServicesDto;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TherapyServicesService {
    Page<TherapyServicesDto> allByTherapy(Pageable pageable, Long id);

    Page<TherapyServicesDto> all(Pageable pageable, Status status);

    void create(Long id, TherapyServicesDto therapyServicesDto);

    void update(Long id, TherapyServicesDto therapyServicesDto);

    void delete(Long id);

    void changeStatus(Long id, ActiveInactiveDto activeInactiveDto);

}
