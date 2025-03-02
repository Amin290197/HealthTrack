package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.dao.CertificateEntity;
import com.example.rehabilitationandintegration.model.CertificateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CertificateService {
    Page<CertificateDto> all(Pageable pageable);

    CertificateDto get(Long id);

    Page<CertificateDto> specialistCertificates(Pageable pageable, Long specialistId);

    void create(CertificateDto certificateDto, Long specialistId);

    void update(Long id, CertificateDto certificateDto);

    void delete(Long id);
}
