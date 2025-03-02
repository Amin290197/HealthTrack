package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.PriceDto;
import com.example.rehabilitationandintegration.model.request.ActiveInactiveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PriceService {
    Page<PriceDto> all(Pageable pageable, Status status);

    PriceDto get(Long id);

    void create(PriceDto priceDto);

    void update(Long id, PriceDto priceDto);

//    void delete(Long id);

    void changeStatus(Long id, ActiveInactiveDto status);

    Page<PriceDto> byTherapy(Long id, Pageable pageable);
}
