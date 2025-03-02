package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.TherapyEntity;
import com.example.rehabilitationandintegration.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TherapyRepository extends JpaRepository<TherapyEntity, Long> {
    Page<TherapyEntity> findAllByStatus(Pageable pageable, Status status);



}
