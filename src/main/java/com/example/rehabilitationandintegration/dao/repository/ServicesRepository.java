package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.TherapyEntity;
import com.example.rehabilitationandintegration.dao.TherapyServicesEntity;
import com.example.rehabilitationandintegration.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<TherapyServicesEntity, Long> {
    Page<TherapyServicesEntity> findAllByTherapy(Pageable pageable, TherapyEntity therapyEntity);

    Page<TherapyServicesEntity> findAllByStatus(Pageable pageable, Status status);
}
