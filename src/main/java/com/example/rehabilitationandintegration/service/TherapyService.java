package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import com.example.rehabilitationandintegration.model.response.TherapyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TherapyService {
    Page<TherapyDto> getAll(Pageable pageable, Status status);

    TherapyDto get(Long id);

;   void create(TherapyDto therapyDto);

    void update(TherapyDto therapyDto, Long id);

    void delete(Long id);

    void changeStatus(Long id, ActiveInactiveDto status);
}
