package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.TaskEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.dao.repository.SpecialistRepository;
import com.example.rehabilitationandintegration.dao.repository.TaskRepository;
import com.example.rehabilitationandintegration.dao.repository.UserRepository;
import com.example.rehabilitationandintegration.enums.TaskStatus;
import com.example.rehabilitationandintegration.exception.AlreadyException;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.TaskMapper;
import com.example.rehabilitationandintegration.model.TaskDto;
import com.example.rehabilitationandintegration.specification.TaskFilter;
import com.example.rehabilitationandintegration.model.request.TaskCreateDto;
import com.example.rehabilitationandintegration.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final SpecialistRepository specialistRepository;
    private final UserRepository userRepository;

    @Override
    public Page<TaskDto> patient(Pageable pageable, Long userId) {
        log.info("Action.getTasksByPatientId started for patient {}", userId);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        Page<TaskEntity> taskEntityPage = taskRepository.findAllByUser(pageable, userEntity);
        List<TaskDto> taskDtoList = taskEntityPage.stream().map(taskMapper::toDto).toList();
        PageImpl<TaskDto> taskDtoPage = new PageImpl<>(taskDtoList, pageable, taskEntityPage.getTotalElements());
        log.info("Action.getTasksByPatientId ended for patient {}", userId);
        return taskDtoPage;
    }

    @Override
    public Page<TaskDto> specialist(Pageable pageable, Long id) {
        log.info("Action.getTasksBySpecialist started for specialist {}", id);
        SpecialistEntity specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        Page<TaskEntity> taskEntityPage = taskRepository.findAllBySpecialist(pageable, specialist);
        List<TaskDto> taskDtoList = taskEntityPage.stream().map(taskMapper::toDto).toList();
        PageImpl<TaskDto> taskDtoPage = new PageImpl<>(taskDtoList, pageable, taskEntityPage.getTotalElements());
        log.info("Action.getTasksBySpecialist ended for specialist {}", id);
        return taskDtoPage;
    }

    @Override
    public Page<TaskDto> all(Pageable pageable, TaskFilter taskFilter) {
        log.info("Action.log.all started for tasks");
        var specification = Specification.where(taskFilter);
        Page<TaskEntity> taskEntityPage = taskRepository.findAll(specification, pageable);
        if (taskEntityPage.isEmpty()) throw new ResourceNotFoundException("Not found any task");
        List<TaskDto> taskDtoList = taskEntityPage.stream().map(taskMapper::toDto).toList();
        PageImpl<TaskDto> taskDtoPage = new PageImpl<>(taskDtoList, pageable, taskEntityPage.getTotalElements());
        log.info("Action.all ended for tasks");
        return taskDtoPage;
    }

    @Override
    public TaskDto get(Long id) {
        log.info("Action.log.get started for task {}", id);
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        TaskDto taskDto = taskMapper.toDto(taskEntity);
        log.info("Action.log.get ended for task {}", id);
        return taskDto;
    }

    @Override
    public void update(Long id, TaskDto taskDto) {
        log.info("Action.log.update started for task {}", id);
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        taskEntity = taskMapper.forUpdate(taskDto, taskEntity);
        taskRepository.save(taskEntity);
        log.info("Action.log.update ended for task {}", id);
    }

    @Override
    public void changeStatus(Long id, TaskStatus status) {
        log.info("Action.log.changeStatus started for task {}", id);
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (status == taskEntity.getStatus()) throw new AlreadyException("Task already has this status");
        taskEntity.setStatus(status);
        taskRepository.save(taskEntity);
        log.info("Action.log.changeStatus ended for task {}", id);
    }

    @Override
    public void createForPatient(TaskCreateDto taskDto, Long userId, Long specialistId) {
        log.info("Action.log.createForPatient started for user {}", userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        SpecialistEntity specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        TaskEntity taskEntity = taskMapper.toEntity(taskDto);
        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        taskEntity.setUser(user);
        taskEntity.setSpecialist(specialist);
        taskRepository.save(taskEntity);
        log.info("Action.log.createForPatient ended for patient {}", userId);
    }
}
