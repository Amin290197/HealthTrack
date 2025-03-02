package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.CertificateEntity;
import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<CertificateEntity, Long> {
    Page<CertificateEntity> findAllBySpecialist(Pageable pageable, SpecialistEntity specialist);
}
