package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.specification.AppointmentFilter;
import com.example.rehabilitationandintegration.model.request.AppointmentChangeDto;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;
import com.example.rehabilitationandintegration.model.response.AppointmentResponseDto;
import com.example.rehabilitationandintegration.service.impl.AppointmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentServiceImpl appointmentService;

    @PostMapping("/all")
    public ResponseEntity<Page<AppointmentResponseDto>> getAllAppointments(Pageable pageable,
                                                                          @RequestBody AppointmentFilter appointmentFilter) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.all(pageable, appointmentFilter));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<AppointmentResponseDto>> patientAppointments(Pageable pageable, @PathVariable Long id,
                                                            @RequestParam MeetingAndAppointmentStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.userAppointments(pageable, id, status));
    }

    @GetMapping("/specialist/{id}")
    public ResponseEntity<Page<AppointmentResponseDto>> specialistAppointments(Pageable pageable, @PathVariable Long id,
                                                               @RequestParam MeetingAndAppointmentStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.specialistAppointments(pageable, id, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointment(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.get(id));
    }

    @PostMapping("/register/user/{userId}/specialist{id}")
    public ResponseEntity<Void> register(@PathVariable Long userId, @PathVariable Long id,
                         @RequestBody AppointmentRequestList appointmentRequestList) {
        appointmentService.register(userId, id, appointmentRequestList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/cancel/user/{userId}")
    public ResponseEntity<Void> cancel(@PathVariable Long userId, @PathVariable Long id) {
        appointmentService.cancel(userId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/change/user/{userId}")
    public ResponseEntity<Void> change(@PathVariable Long userId, @PathVariable Long id,
                       @RequestBody AppointmentChangeDto appointmentChangeDto) {
        appointmentService.change(userId, id, appointmentChangeDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

