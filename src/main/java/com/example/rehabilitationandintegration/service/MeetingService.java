package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.model.MeetingDto;
import com.example.rehabilitationandintegration.specification.MeetingFilter;
import com.example.rehabilitationandintegration.model.response.MeetingResponseDto;
import com.example.rehabilitationandintegration.model.request.MeetingCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetingService {
    MeetingResponseDto create(Long userId, MeetingCreateDto meetingDto);

    Page<MeetingDto> getAll(Pageable pageable, MeetingFilter meetingFilter);

    Page<MeetingDto> userMeetings(Long userId, Pageable pageable, MeetingAndAppointmentStatus status);

    void cancel(Long userId, Long id);

    MeetingResponseDto changeDate(Long userId, Long id, MeetingCreateDto meetingCreateDto);

    void complete();
}
