package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.model.TaskDto;
import com.example.rehabilitationandintegration.specification.TaskFilter;
import com.example.rehabilitationandintegration.model.request.TaskCreateDto;
import com.example.rehabilitationandintegration.service.TaskService;
import com.example.rehabilitationandintegration.validation.ValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/patient/{id}")
    public ResponseEntity<Page<TaskDto>> getPatientTasks(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.patient(pageable, id));
    }

    @GetMapping("/specialist/{id}")
    public ResponseEntity<Page<TaskDto>> specialist(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.specialist(pageable, id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<TaskDto>> all(Pageable pageable, @RequestBody TaskFilter taskFilter) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.all(pageable, taskFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> get(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.get(id));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid TaskDto taskDto) {
        taskService.update(id, taskDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/create/patient/{patientId}/specialist/{specialistId}")
    public ResponseEntity<Void> createForPatient(@RequestBody @Valid TaskCreateDto taskDto, @PathVariable Long patientId,
                                 @PathVariable Long specialistId) {
        taskService.createForPatient(taskDto, patientId, specialistId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
