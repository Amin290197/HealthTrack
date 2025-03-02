package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.AppointmentEntity;
import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>, JpaSpecificationExecutor<AppointmentEntity> {
    List<AppointmentEntity> findAllBySpecialistAndStatusAndDay(SpecialistEntity specialist
            , MeetingAndAppointmentStatus status, LocalDate day);

    List<AppointmentEntity> findAllBySpecialistAndStatus(SpecialistEntity specialist,
                                                         MeetingAndAppointmentStatus status);

    Page<AppointmentEntity> findAllBySpecialistAndStatus(Pageable pageable, SpecialistEntity specialist,
                                                         MeetingAndAppointmentStatus status);

    Page<AppointmentEntity> findAllBySpecialist(Pageable pageable, SpecialistEntity specialist);


    Page<AppointmentEntity> findAllByUserAndStatus(Pageable pageable, UserEntity user,
                                                         MeetingAndAppointmentStatus status);

    Page<AppointmentEntity> findAllByUser(Pageable pageable, UserEntity user);

    Optional<AppointmentEntity> findByIdAndStatus(Long id, MeetingAndAppointmentStatus status);

    List<AppointmentEntity> findAllByStatus(MeetingAndAppointmentStatus status);
}
