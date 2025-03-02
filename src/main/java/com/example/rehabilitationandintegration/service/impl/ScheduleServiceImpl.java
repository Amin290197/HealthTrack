package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.ScheduleEntity;
import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.repository.ScheduleRepository;
import com.example.rehabilitationandintegration.dao.repository.SpecialistRepository;
import com.example.rehabilitationandintegration.dao.AppointmentEntity;
import com.example.rehabilitationandintegration.dao.repository.AppointmentRepository;
import com.example.rehabilitationandintegration.enums.DayOfWeekEnum;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.AlreadyException;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.mapper.ScheduleMapper;
import com.example.rehabilitationandintegration.model.response.FreeScheduleResponse;
import com.example.rehabilitationandintegration.model.ScheduleDto;
import com.example.rehabilitationandintegration.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final SpecialistRepository specialistRepository;
    private final ScheduleMapper scheduleMapper;

    @Value("${lunchStart}")
    private LocalTime lunchStart;
    @Value("${lunchEnd}")
    private LocalTime lunchEnd;


    @Override
    public Page<FreeScheduleResponse> specialistFreeSchedule(Pageable pageable, Long id) {
        SpecialistEntity specialist = specialistRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));

        List<ScheduleEntity> schedule = scheduleRepository.findAllBySpecialist(specialist);
        List<AppointmentEntity> specialistFullTimes = appointmentRepository
                .findAllBySpecialistAndStatus(specialist, MeetingAndAppointmentStatus.SCHEDULED);

        Map<DayOfWeekEnum, List<LocalTime[]>> dayIntervalsMap = new HashMap<>();

        for (ScheduleEntity currentSchedule : schedule) {
            LocalTime workStartTime = currentSchedule.getStartTime();
            LocalTime workEndTime = currentSchedule.getEndTime();
            DayOfWeekEnum currentDay = DayOfWeekEnum.fromNumber(currentSchedule.getDay());

            List<AppointmentEntity> appointmentsForDay = getAppointmentsForDay(specialistFullTimes, currentSchedule.getDay());
            List<LocalTime[]> freeIntervals = calculateFreeIntervals(appointmentsForDay, workStartTime, workEndTime);

            dayIntervalsMap.computeIfAbsent(currentDay, k -> new ArrayList<>()).addAll(freeIntervals);
        }

        List<FreeScheduleResponse> freeScheduleResponse = dayIntervalsMap.entrySet().stream()
                .map(entry -> new FreeScheduleResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        freeScheduleResponse.sort(Comparator.comparing(FreeScheduleResponse::getDay));

        return new PageImpl<>(freeScheduleResponse, pageable, freeScheduleResponse.size());
    }


    private List<AppointmentEntity> getAppointmentsForDay(List<AppointmentEntity> specialistFullTimes, Integer day) {
        return specialistFullTimes.stream()
                .filter(appointment -> appointment.getDay().getDayOfWeek().getValue() == day)
                .sorted(Comparator.comparing(AppointmentEntity::getStartTime))
                .collect(Collectors.toList());
    }

    private List<LocalTime[]> calculateFreeIntervals(List<AppointmentEntity> appointments, LocalTime workStartTime, LocalTime workEndTime) {
        List<LocalTime[]> freeIntervals = new ArrayList<>();
        LocalTime lastEndTime = workStartTime;

        for (AppointmentEntity appointment : appointments) {
            LocalTime appointmentStartTime = appointment.getStartTime();
            LocalTime appointmentEndTime = appointment.getEndTime();

            if (lastEndTime.isBefore(appointmentStartTime)) {
                freeIntervals.addAll(getIntervalsBeforeAppointment(lastEndTime, appointmentStartTime));
            }

            lastEndTime = appointmentEndTime;

            if (appointmentEndTime.equals(lunchStart)) {
                lastEndTime = lunchEnd;
            }
        }

        freeIntervals.addAll(getIntervalsAfterAppointments(lastEndTime, workEndTime));
        return freeIntervals;
    }

    private List<LocalTime[]> getIntervalsBeforeAppointment(LocalTime lastEndTime, LocalTime appointmentStartTime) {
        List<LocalTime[]> intervals = new ArrayList<>();

        if (appointmentStartTime.isBefore(lunchStart)) {
            intervals.add(new LocalTime[]{lastEndTime, appointmentStartTime});
        }

        if (lastEndTime.isBefore(lunchStart) && appointmentStartTime.isAfter(lunchEnd)) {
            intervals.add(new LocalTime[]{lastEndTime, lunchStart});
            lastEndTime = lunchEnd;
        } else if (appointmentStartTime.isAfter(lunchEnd)) {
            intervals.add(new LocalTime[]{lastEndTime, appointmentStartTime});
        }

        return intervals;
    }

    private List<LocalTime[]> getIntervalsAfterAppointments(LocalTime lastEndTime, LocalTime workEndTime) {
        List<LocalTime[]> intervals = new ArrayList<>();

        if (lastEndTime.equals(lunchStart)) {
            lastEndTime = lunchEnd;
        }

        if (lastEndTime.isBefore(lunchStart)) {
            intervals.add(new LocalTime[]{lastEndTime, lunchStart});
        }

        if (lastEndTime.isBefore(lunchEnd)) {
            lastEndTime = lunchEnd;
        }

        if (lastEndTime.isBefore(workEndTime)) {
            intervals.add(new LocalTime[]{lastEndTime, workEndTime});
        }

        return intervals;
    }



    @Override
    public Page<ScheduleDto> getSpecialistSchedule(Pageable pageable, Long id) {
        log.info("Action.log.getSpecialistSchedule started for specialist {}", id);
        SpecialistEntity specialistEntity = specialistRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        Page<ScheduleEntity> scheduleEntityPage = scheduleRepository.findAllBySpecialist(pageable, specialistEntity);
        if (scheduleEntityPage.isEmpty()) throw new ResourceNotFoundException("Schedule not found");
        List<ScheduleDto> scheduleDtoList = scheduleEntityPage.stream()
                .map(scheduleMapper::toDto).toList();
        PageImpl<ScheduleDto> scheduleDtoPage =
                new PageImpl<>(scheduleDtoList, pageable, scheduleEntityPage.getTotalElements());
        log.info("Action.log.getSpecialistSchedule ended for specialist {}", id);
        return scheduleDtoPage;
    }

    @Override
    public void create(Long specialistId, List<ScheduleDto> scheduleDto) {
        log.info("Action.createSchedule started for Specialist {}", specialistId);

        SpecialistEntity specialistEntity = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));

        scheduleDto.forEach(schedule -> {
            ScheduleEntity scheduleEntity = scheduleMapper.toEntity(schedule);
            scheduleEntity.setStatus(Status.ACTIVE);
            scheduleEntity.setSpecialist(specialistEntity);
            scheduleRepository.save(scheduleEntity);
        });
        log.info("Action.createSchedule ended for Specialist {}", specialistId);
    }


    @Override
    public void update(Long id, ScheduleDto scheduleDto) {

    }

    @Override
    public void delete(Long id) {
        log.info("Action.deleteSchedule started for Schedule {}", id);
        ScheduleEntity scheduleEntity = scheduleRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Schedule not found"));
        if (scheduleEntity.getStatus() != Status.DELETED){
            scheduleEntity.setStatus(Status.DELETED);
            scheduleRepository.save(scheduleEntity);
        }
        else throw new AlreadyException("Schedule already deleted");
        log.info("Action.deleteSchedule ended for Schedule {}", id);
    }


}