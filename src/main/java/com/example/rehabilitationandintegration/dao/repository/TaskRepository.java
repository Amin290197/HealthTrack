package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.TaskEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findAllByUser(Pageable pageable, UserEntity user);

    Page<TaskEntity> findAllBySpecialist(Pageable pageable, SpecialistEntity specialist);

    Page<TaskEntity> findAll(Specification specification, Pageable pageable);



}
