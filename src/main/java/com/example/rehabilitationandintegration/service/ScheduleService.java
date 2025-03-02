package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.model.response.FreeScheduleResponse;
import com.example.rehabilitationandintegration.model.ScheduleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {

    Page<FreeScheduleResponse> specialistFreeSchedule(Pageable pageable, Long id);

    Page<ScheduleDto> getSpecialistSchedule(Pageable pageable, Long id);

    void create(Long specialistId, List<ScheduleDto> scheduleDto);

    void update(Long id, ScheduleDto scheduleDto);

    void delete(Long id);
}
