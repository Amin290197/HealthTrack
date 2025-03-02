package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.specification.AppointmentFilter;
import com.example.rehabilitationandintegration.model.request.AppointmentChangeDto;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;
import com.example.rehabilitationandintegration.model.response.AppointmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    Page<AppointmentResponseDto> all(Pageable pageable, AppointmentFilter appointmentFilter);

    AppointmentResponseDto get(Long id);

    Page<AppointmentResponseDto> userAppointments(Pageable pageable, Long id, MeetingAndAppointmentStatus status);

    Page<AppointmentResponseDto> specialistAppointments(Pageable pageable, Long id, MeetingAndAppointmentStatus status);

    void register(Long userId, Long id, AppointmentRequestList appointmentRequestList);

    void cancel(Long userId, Long id);

    void change(Long userId, Long id, AppointmentChangeDto appointmentChangeDto);

    void complete();
}
