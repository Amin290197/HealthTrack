package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.TaskStatus;
import com.example.rehabilitationandintegration.model.TaskDto;
import com.example.rehabilitationandintegration.specification.TaskFilter;
import com.example.rehabilitationandintegration.model.request.TaskCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<TaskDto> patient(Pageable pageable, Long userId);

    Page<TaskDto> specialist(Pageable pageable, Long id);

    Page<TaskDto> all(Pageable pageable, TaskFilter taskFilter);

    TaskDto get(Long id);

    void update(Long id, TaskDto taskDto);

    void changeStatus(Long id, TaskStatus status);

    void createForPatient(TaskCreateDto taskDto, Long userId, Long specialistId);
}
