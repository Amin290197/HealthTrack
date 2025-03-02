package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.MeetingEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<MeetingEntity, Long>, JpaSpecificationExecutor<MeetingEntity> {
    Page<MeetingEntity> findByUser(UserEntity user, Pageable pageable);

    Page<MeetingEntity> findByUserAndStatus(UserEntity user, Pageable pageable, MeetingAndAppointmentStatus status);

    Optional<MeetingEntity> findByIdAndStatus(Long id, MeetingAndAppointmentStatus status);

    List<MeetingEntity> findAllByStatus(MeetingAndAppointmentStatus status);
}
