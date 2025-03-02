package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.AppointmentEntity;
import com.example.rehabilitationandintegration.dao.MeetingEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.dao.repository.MeetingRepository;
import com.example.rehabilitationandintegration.dao.repository.UserRepository;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.exception.*;
import com.example.rehabilitationandintegration.mapper.MeetingMapper;
import com.example.rehabilitationandintegration.model.MeetingDto;
import com.example.rehabilitationandintegration.specification.MeetingFilter;
import com.example.rehabilitationandintegration.model.response.MeetingResponseDto;
import com.example.rehabilitationandintegration.model.request.MeetingCreateDto;
import com.example.rehabilitationandintegration.service.MeetingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;
    private final UserRepository userRepository;


    @Value("${lunchStart}")
    private LocalTime lunchStart;
    @Value("${lunchEnd}")
    private LocalTime lunchEnd;

    @Value("${workStart}")
    private LocalTime workStart;
    @Value("${workEnd}")
    private LocalTime workEnd;


    @Override
    public MeetingResponseDto create(Long userId, MeetingCreateDto meetingCreateDto) {
        log.info("Action.log.createMeeting started for user {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateMeetingDate(meetingCreateDto.getDate(), meetingCreateDto.getTime());

        List<MeetingEntity> meetingEntityList = meetingRepository.findAll();
        if (!isTimeSlotAvailable(meetingCreateDto.getDate(), meetingCreateDto.getTime(), meetingEntityList)) {
            throw new AlreadyException("The requested time slot is already taken.");
        }

        MeetingEntity meetingEntity = meetingMapper.toEntityForCreate(meetingCreateDto);
        meetingEntity.setCreated(LocalDateTime.now());
        meetingEntity.setUser(user);
        meetingEntity.setStatus(MeetingAndAppointmentStatus.SCHEDULED);

        meetingRepository.save(meetingEntity);

        MeetingResponseDto response = MeetingResponseDto.builder().meetingDay(meetingEntity.getDate())
                .meetingTime(meetingEntity.getTime()).build();
        log.info("Action.log.createMeeting ended for user {}", userId);
        return response;
    }

    @Override
    public Page<MeetingDto> getAll(Pageable pageable, MeetingFilter meetingFilter) {
        log.info("Action.log.getAll started");
        var specification = Specification.where(meetingFilter);
        Page<MeetingEntity> meetingEntityPage = meetingRepository.findAll(specification, pageable);
        if (meetingEntityPage.isEmpty()) {
            throw new ResourceNotFoundException("Not found meetings");
        }
        List<MeetingDto> meetingDtoList = meetingEntityPage.stream().map(meetingMapper::toDto).toList();
        PageImpl<MeetingDto> meetingDtoPage = new PageImpl<>(meetingDtoList, pageable, meetingEntityPage.getTotalElements());
        log.info("Action.log.getAll started");
        return meetingDtoPage;
    }

    @Override
    public Page<MeetingDto> userMeetings(Long userId, Pageable pageable, MeetingAndAppointmentStatus status) {
        log.info("Action.log.userMeetings started for user {}", userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Page<MeetingEntity> meetingEntityPage;
        List<MeetingDto> meetingDtoList;
        PageImpl<MeetingDto> meetingDtoPage;

        if (status == null) {
            meetingEntityPage = meetingRepository.findByUser(user, pageable);
            if (meetingEntityPage == null) {throw new ResourceNotFoundException("User not has any meetings");}
            meetingDtoList = meetingEntityPage.stream().map(meetingMapper::toDto).toList();
            meetingDtoPage = new PageImpl<>(meetingDtoList, pageable, meetingEntityPage.getTotalElements());
        }else {
            meetingEntityPage = meetingRepository.findByUserAndStatus(user, pageable, status);
            if (meetingEntityPage == null) {throw new ResourceNotFoundException("User not has any meetings");}
            meetingDtoList = meetingEntityPage.stream().map(meetingMapper::toDto).toList();
            meetingDtoPage = new PageImpl<>(meetingDtoList, pageable, meetingEntityPage.getTotalElements());
        }
        log.info("Action.log.userMeetings ended for user {}", userId);
        return meetingDtoPage;
    }


    @Override
    public void cancel(Long userId, Long id) {
        log.info("Action.log.cancelMeeting started for user {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MeetingEntity meetingEntity = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));

        if (!meetingEntity.getUser().getId().equals(user.getId())) {
            throw new OwnershipException("You do not have permission to cancel this meeting.");
        }

        if (!(meetingEntity.getDate().isAfter(LocalDate.now())
                && meetingEntity.getTime().isBefore(LocalTime.now().plusHours(2)))) {
            throw new CancellationException("You cannot cancel a meeting less than an hour before it starts.");
        }

        meetingEntity.setStatus(MeetingAndAppointmentStatus.CANCELED);
        meetingRepository.save(meetingEntity);
        log.info("Action.log.cancelMeeting ended for user {}", userId);
    }


    @Override
    public MeetingResponseDto changeDate(Long userId, Long id, MeetingCreateDto meetingCreateDto) {
        log.info("Action.log.changeDate started for user {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MeetingEntity currentMeeting = meetingRepository.findByIdAndStatus(id, MeetingAndAppointmentStatus.SCHEDULED)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found"));

        if (!currentMeeting.getUser().equals(user)) {
            throw new OwnershipException("You do not have permission to change this meeting.");
        }

//        LocalDateTime requestedDateTime = LocalDateTime.of(meetingCreateDto.getDate(), meetingCreateDto.getTime());
        validateMeetingDate(meetingCreateDto.getDate(), meetingCreateDto.getTime());

        List<MeetingEntity> meetingEntityList = meetingRepository.findAll();
        if (!isTimeSlotAvailable(meetingCreateDto.getDate(), meetingCreateDto.getTime(), meetingEntityList)) {
            throw new AlreadyException("The requested time slot is already taken.");
        }

        currentMeeting.setDate(meetingCreateDto.getDate());
        currentMeeting.setTime(roundToNearestHalfHour(meetingCreateDto.getTime()));
        currentMeeting.setCreated(LocalDateTime.now());
        meetingRepository.save(currentMeeting);

        MeetingResponseDto response = MeetingResponseDto.builder().meetingDay(currentMeeting.getDate())
                .meetingTime(currentMeeting.getTime()).build();
        log.info("Action.log.changeDate ended for user {}", userId);
        return response;
    }

    @Override
    public void complete() {
        log.info("Action.log.completeMeeting started");

        List<MeetingEntity> meetingEntityList = meetingRepository
                .findAllByStatus(MeetingAndAppointmentStatus.SCHEDULED);

        for (MeetingEntity meetingEntity : meetingEntityList) {
            if (meetingEntity.getDate().isBefore(LocalDate.now())) {
                meetingEntity.setStatus(MeetingAndAppointmentStatus.COMPLETED);
                meetingRepository.save(meetingEntity);
            }
        }

//        List<MeetingEntity> updatedMeetings = meetingEntityList.stream()
//                .filter(a -> !a.getDate().isAfter(LocalDate.now()))
//                .peek(a -> a.setStatus(MeetingAndAppointmentStatus.COMPLETED))
//                .toList();
//
//        if (!updatedMeetings.isEmpty()) {
//            meetingRepository.saveAll(updatedMeetings);
//        }

        log.info("Action.log.completeMeeting ended");
    }


    private void validateMeetingDate(LocalDate date, LocalTime time) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new TimeNotAllowedException("Meetings cannot be scheduled on weekends.");
        }

        LocalDate today = LocalDate.now();
        if (date.isBefore(today.plusDays(3))) {
            throw new InvalidTimeException("Meetings must be scheduled at least 3 days in advance.");
        }

        if (date.isAfter(today.plusMonths(1))) {
            throw new InvalidTimeException("Meetings cannot be scheduled more than a month in advance.");
        }

        if (time.isBefore(workStart) || time.isAfter(workEnd)) {
            throw new TimeNotAllowedException("Meetings must be scheduled during working hours (9:00 to 17:00).");
        }

        if (!time.isBefore(lunchStart) && !time.isAfter(lunchEnd)) {
            throw new TimeNotAllowedException("Meetings cannot be scheduled during lunch break (13:00 to 14:00).");
        }
    }


    private boolean isTimeSlotAvailable(LocalDate date, LocalTime time, List<MeetingEntity> meetings) {
        for (MeetingEntity meeting : meetings) {
            if (meeting.getDate().equals(date) && meeting.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }


    private LocalTime roundToNearestHalfHour(LocalTime time) {
        int minute = time.getMinute();
        if (minute < 15) {
            return time.withMinute(0);
        } else if (minute < 45) {
            return time.withMinute(30);
        } else {
            return time.plusHours(1).withMinute(0);
        }
    }


}