package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.model.SpecialtyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecialtyService {
    Page<SpecialtyDto> all(Pageable pageable);

    SpecialtyDto get(Long id);

    void change(Long id, Long specialtyId);
}
