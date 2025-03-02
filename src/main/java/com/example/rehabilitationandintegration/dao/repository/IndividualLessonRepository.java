package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualLessonRepository extends JpaRepository<PriceEntity, Long> {
    PriceEntity findByDuration(Integer duration);
}
