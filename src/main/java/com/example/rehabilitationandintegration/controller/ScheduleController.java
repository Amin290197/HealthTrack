package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.model.response.FreeScheduleResponse;
import com.example.rehabilitationandintegration.model.ScheduleDto;
import com.example.rehabilitationandintegration.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/specialist/{id}/freeTime")
    public ResponseEntity<Page<FreeScheduleResponse>> getSpecialistFreeSchedule(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.specialistFreeSchedule(pageable, id));
    }

    @GetMapping("/specialist/{id}")
    public ResponseEntity<Page<ScheduleDto>> getSpecialistSchedule(Pageable pageable, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSpecialistSchedule(pageable, id));
    }

    @PostMapping("/create/specialist/{id}")
    public ResponseEntity<Void> create(@PathVariable Long id, @RequestBody @Valid List<ScheduleDto> scheduleDto) {
        scheduleService.create(id, scheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ScheduleDto scheduleDto) {
        scheduleService.update(id, scheduleDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}