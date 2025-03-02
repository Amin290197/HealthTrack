package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.ScheduleEntity;
import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    Page<ScheduleEntity> findAllBySpecialist(Pageable pageable, SpecialistEntity specialist);

    List<ScheduleEntity> findAllBySpecialist(SpecialistEntity specialist);

    Optional<ScheduleEntity> findBySpecialistAndDay(SpecialistEntity specialist, Integer day);
}
