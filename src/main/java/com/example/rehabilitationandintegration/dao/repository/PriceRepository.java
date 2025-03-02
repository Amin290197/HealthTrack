package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.PriceEntity;
import com.example.rehabilitationandintegration.dao.TherapyEntity;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.model.PriceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    List<PriceEntity> findAllByStatus(Status status);

    Page<PriceEntity> findAllByStatus(Pageable pageable, Status status);

    Page<PriceEntity> findAllByTherapyAndStatus(Pageable pageable, TherapyEntity therapy, Status status);

    Optional<PriceEntity> findByDuration(Integer duration);
}
