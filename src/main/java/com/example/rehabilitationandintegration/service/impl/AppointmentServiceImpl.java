package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.*;
import com.example.rehabilitationandintegration.dao.repository.*;
import com.example.rehabilitationandintegration.enums.DayOfWeekEnum;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.*;
import com.example.rehabilitationandintegration.mapper.AppointmentMapper;
import com.example.rehabilitationandintegration.service.MailService;
import com.example.rehabilitationandintegration.service.PaymentService;
import com.example.rehabilitationandintegration.specification.AppointmentFilter;
import com.example.rehabilitationandintegration.model.request.AppointmentChangeDto;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;
import com.example.rehabilitationandintegration.model.request.AppointmentRequest;
import com.example.rehabilitationandintegration.model.response.AppointmentResponseDto;
import com.example.rehabilitationandintegration.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PriceRepository priceRepository;
    private final ScheduleRepository scheduleRepository;
    private final SpecialistRepository specialistRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final MailService mailService;

    @Value("${lunchStart}")
    private LocalTime lunchStart;
    @Value("${lunchEnd}")
    private LocalTime lunchEnd;

    @Override
    public Page<AppointmentResponseDto> all(Pageable pageable, AppointmentFilter appointmentFilter) {
        log.info("Action.log.getAllAppointments started");
        var specification = Specification.where(appointmentFilter);
        Page<AppointmentEntity> appointmentEntitiesPage = appointmentRepository.findAll(specification, pageable);
        if (appointmentEntitiesPage.isEmpty()) throw new ResourceNotFoundException("Not found appointments for request");
        List<AppointmentResponseDto> appointmentResponseDtoList =
                appointmentEntitiesPage.stream().map(appointmentMapper::toResponse).toList();
        PageImpl<AppointmentResponseDto> appointmentResponseDtoPage =
                new PageImpl<>(appointmentResponseDtoList, pageable, appointmentEntitiesPage.getTotalElements());
        log.info("Action.log.getAllAppointments ended");
        return appointmentResponseDtoPage;
    }

    @Override
    public AppointmentResponseDto get(Long id) {
        log.info("Action.log.getAppointment started for appointment {}", id);
        AppointmentEntity appointmentEntity = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found appointment"));
        AppointmentResponseDto appointmentResponseDto = appointmentMapper.toResponse(appointmentEntity);
        log.info("Action.log.getAppointment ended for appointment {}", id);
        return appointmentResponseDto;
    }

    @Override
    public Page<AppointmentResponseDto> userAppointments(Pageable pageable, Long id, MeetingAndAppointmentStatus status) {
        log.info("Action.log.getSpecialistAppointments started for patient {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Page<AppointmentEntity> patientAppointmentsPage;
        List<AppointmentResponseDto> patientAppointmentsDtoList;
        PageImpl<AppointmentResponseDto> patientAppointmentsDtoPage;

        if (status == null) {
            patientAppointmentsPage = appointmentRepository
                    .findAllByUser(pageable, user);
            if (patientAppointmentsPage.isEmpty())
                throw new ResourceNotFoundException("Patient not have appointments");
            patientAppointmentsDtoList = patientAppointmentsPage.stream()
                    .map(appointmentMapper::toResponse).toList();
            patientAppointmentsDtoPage = new PageImpl<>(patientAppointmentsDtoList
                    , pageable, patientAppointmentsPage.getTotalElements());
        }
        else {
            patientAppointmentsPage = appointmentRepository
                    .findAllByUserAndStatus(pageable, user, status);
            if (patientAppointmentsPage.isEmpty())
                throw new ResourceNotFoundException("Patient not have appointments");
            patientAppointmentsDtoList = patientAppointmentsPage.stream()
                    .map(appointmentMapper::toResponse).toList();
            patientAppointmentsDtoPage = new PageImpl<>(patientAppointmentsDtoList
                    , pageable, patientAppointmentsPage.getTotalElements());
        }

        log.info("Action.log.getSpecialistAppointments ended for patient {}", id);
        return patientAppointmentsDtoPage;
    }

    @Override
    public Page<AppointmentResponseDto> specialistAppointments(Pageable pageable, Long id, MeetingAndAppointmentStatus status) {
        log.info("Action.log.getSpecialistAppointments started for specialist {}", id);

        SpecialistEntity specialistEntity = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));

        Page<AppointmentEntity> specialistAppointmentsPage;
        List<AppointmentResponseDto> specialistAppointmentsDtoList;
        PageImpl<AppointmentResponseDto> specialistAppointmentsDtoPage;

        if (status == null) {
            specialistAppointmentsPage = appointmentRepository
                    .findAllBySpecialist(pageable, specialistEntity);
            if (specialistAppointmentsPage.isEmpty())
                throw new ResourceNotFoundException("Specialist not have appointments");
            specialistAppointmentsDtoList = specialistAppointmentsPage.stream()
                    .map(appointmentMapper::toResponse).toList();
            specialistAppointmentsDtoPage = new PageImpl<>(specialistAppointmentsDtoList
                    , pageable, specialistAppointmentsPage.getTotalElements());
        } else {
            specialistAppointmentsPage = appointmentRepository
                    .findAllBySpecialistAndStatus(pageable, specialistEntity, status);
            if (specialistAppointmentsPage.isEmpty())
                throw new ResourceNotFoundException("Specialist not have appointments");
            specialistAppointmentsDtoList = specialistAppointmentsPage.stream()
                    .map(appointmentMapper::toResponse).toList();
            specialistAppointmentsDtoPage = new PageImpl<>(specialistAppointmentsDtoList
                    , pageable, specialistAppointmentsPage.getTotalElements());
        }

        log.info("Action.log.getSpecialistAppointments ended for specialist {}", id);
        return specialistAppointmentsDtoPage;
    }

    @Transactional
    @Override
    public void register(Long userId, Long specialistId, AppointmentRequestList appointmentRequestList) {
        log.info("Action.log.register started for patient {}", userId);

        SpecialistEntity specialist = specialistRepository.findByIdAndStatus(specialistId, Status.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        int lastWeek = 0;
        int week = 1;

        for (int i = 0; i < appointmentRequestList.getAppointmentRequests().size(); i++) {
            AppointmentRequest appointmentRequest = appointmentRequestList.getAppointmentRequests().get(i);

            if (!isValidDuration(appointmentRequest.getDuration())) {
                throw new InvalidTimeException("Invalid duration");
            }

            LocalDate nextWeekSameDay = calculateNextWeekDate(appointmentRequest, week, lastWeek);

            ScheduleEntity specialistSchedule = scheduleRepository.findBySpecialistAndDay(specialist,
                            appointmentRequest.getDay().getNumber())
                    .orElseThrow(() -> new TimeNotAllowedException("It is outside the specialist's working day"));

            if (!isRequestWithinSpecialistSchedule(specialistSchedule, appointmentRequest)) {
                throw new TimeNotAllowedException("Requested time is outside the specialist's working hours.");
            }

            List<AppointmentEntity> reservedAppointments = appointmentRepository
                    .findAllBySpecialistAndStatusAndDay(specialist, MeetingAndAppointmentStatus.SCHEDULED, nextWeekSameDay);

            AppointmentEntity newAppointment =
                    createOrValidateAppointment(nextWeekSameDay, appointmentRequest, reservedAppointments);
            newAppointment.setUser(user);
            newAppointment.setSpecialist(specialist);
            newAppointment.setWeek(appointmentRequestList.getWeek());

            appointmentRepository.save(newAppointment);

            lastWeek = week;
            if (week == appointmentRequestList.getWeek() &&
                    appointmentRequest.equals(appointmentRequestList.getAppointmentRequests().getLast())) {
                break;
            }

            if (i == appointmentRequestList.getAppointmentRequests().size() - 1) {
                week++;
                i = -1;
            }
        }
        Double sum = paymentService.calculateTotal(user, appointmentRequestList);
        mailService.appointmentNotify(user, sum);
        log.info("Action.log.register ended for patient {}", userId);
    }

    @Override
    public void cancel(Long userId, Long id) {
        log.info("Action.log.cancel started for patient {}", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        AppointmentEntity appointmentEntity = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (appointmentEntity.getStatus() == MeetingAndAppointmentStatus.CANCELED) {
            throw new AlreadyException("Appointment already canceled");
        }

        if (!appointmentEntity.getUser().equals(user)) {
            throw new OwnershipException("Appointment does not belong to this user");
        }

        if (LocalTime.now().isAfter(appointmentEntity.getStartTime().minusHours(3))) {
            throw new CancellationException("Cancel failed because less than three hours remain before the appointment.");
        }

        appointmentEntity.setStatus(MeetingAndAppointmentStatus.CANCELED);
        appointmentRepository.save(appointmentEntity);

        log.info("Action.log.cancel completed successfully for patient {}", userId);
    }

    @Override
    public void change(Long userId, Long id, AppointmentChangeDto appointmentChangeDto) {
        log.info("Action.log.change started for patient {}", userId);

//        UserEntity user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        AppointmentEntity appointmentEntity = appointmentRepository.findByIdAndStatus(id, MeetingAndAppointmentStatus.SCHEDULED)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if (!isValidDuration(appointmentChangeDto.getDuration())) {
            throw new InvalidTimeException("Invalid duration");
        }

        DayOfWeek newDay = appointmentChangeDto.getNewDate().getDayOfWeek();
        validateNewDateForChange(appointmentChangeDto, appointmentEntity, newDay);

        ScheduleEntity specialistSchedule = scheduleRepository
                .findBySpecialistAndDay(appointmentEntity.getSpecialist(), newDay.getValue())
                .orElseThrow(() -> new ResourceNotFoundException("Specialist schedule not found"));

        AppointmentRequest appointmentRequest = appointmentMapper.toRequestFromChange(appointmentChangeDto);

        if (!isRequestWithinSpecialistSchedule(specialistSchedule, appointmentRequest)) {
            throw new TimeNotAllowedException("Requested time is outside the specialist's working hours.");
        }

        List<AppointmentEntity> reservedAppointments = appointmentRepository
                .findAllBySpecialistAndStatusAndDay(appointmentEntity.getSpecialist(),
                        MeetingAndAppointmentStatus.SCHEDULED, appointmentChangeDto.getNewDate());

        AppointmentEntity updatedAppointment = createOrValidateAppointment(appointmentChangeDto.getNewDate(),
                appointmentRequest, reservedAppointments);
        updatedAppointment.setId(appointmentEntity.getId());
        appointmentRepository.save(updatedAppointment);

        log.info("Action.log.change completed successfully for patient {}", userId);
    }

    @Override
    public void complete() {
        log.info("Action.log.completeAppointments started");

        List<AppointmentEntity> appointmentEntityList = appointmentRepository
                .findAllByStatus(MeetingAndAppointmentStatus.SCHEDULED);

        for (AppointmentEntity appointmentEntity : appointmentEntityList) {
            if (appointmentEntity.getDay().isBefore(LocalDate.now())) {
                appointmentEntity.setStatus(MeetingAndAppointmentStatus.COMPLETED);
                appointmentRepository.save(appointmentEntity);
            }
        }
//        List<AppointmentEntity> updatedAppointments = appointmentEntityList.stream()
//                .filter(a -> !a.getDay().isAfter(LocalDate.now()))
//                .peek(a -> a.setStatus(MeetingAndAppointmentStatus.COMPLETED))
//                .toList();

//        if (!updatedAppointments.isEmpty()) {
//            appointmentRepository.saveAll(updatedAppointments);
//        }

        log.info("Action.log.completeAppointments ended");
    }


    private AppointmentEntity createOrValidateAppointment(LocalDate date, AppointmentRequest appointmentRequest,
                                                          List<AppointmentEntity> reservedAppointments) {
        log.info("Action.log.createOrValidateAppointment started");

        LocalTime requestStart = appointmentRequest.getStartTime();
        LocalTime requestEnd = requestStart.plusMinutes(appointmentRequest.getDuration());



        if (requestStart.isBefore(lunchEnd) && requestEnd.isAfter(lunchStart)) {
            throw new TimeNotAllowedException("Requested time conflicts with lunch break (13:00 - 14:00).");
        }

        for (AppointmentEntity appointment : reservedAppointments) {
            LocalTime existingStart = appointment.getStartTime();
            LocalTime existingEnd = existingStart.plusMinutes(appointment.getDuration());

            log.info("Checking overlap: Request [{} - {}], Existing [{} - {}]",
                    requestStart, requestEnd, existingStart, existingEnd);
//            if (!(requestEnd.isBefore(existingStart) || requestStart.isAfter(existingEnd))) {
//                throw new IllegalArgumentException("Requested time is already booked.");
//            }
            boolean isOverlapping = requestStart.isBefore(existingEnd) && requestEnd.isAfter(existingStart);

            if (isOverlapping) {
                log.error("Conflict detected: Requested time {} - {} overlaps with existing {} - {}",
                        requestStart, requestEnd, existingStart, existingEnd);
                throw new AlreadyException("Requested time is already booked.");
            }



        }

        AppointmentEntity newAppointment = AppointmentEntity.builder()
                .day(date)
                .startTime(requestStart)
                .endTime(requestEnd)
                .duration(appointmentRequest.getDuration())
                .status(MeetingAndAppointmentStatus.SCHEDULED)
                .build();

        log.info("Action.log.createOrValidateAppointment ended");
        return newAppointment;
    }

    private boolean isRequestWithinSpecialistSchedule(ScheduleEntity schedule, AppointmentRequest request) {
        log.info("Action.log.isRequestWithinSpecialistSchedule started");
        LocalTime start = request.getStartTime();
        LocalTime end = start.plusMinutes(request.getDuration());
        boolean isValid = !start.isBefore(schedule.getStartTime()) && !end.isAfter(schedule.getEndTime())
                && schedule.getDay().equals(request.getDay().getValue());
        log.info("Action.log.isRequestWithinSpecialistSchedule ended");
        return isValid;
    }

    private boolean isValidDuration(Integer duration) {
        log.info("Action.log.isValidDuration started");
        boolean isValid = priceRepository.findAllByStatus(Status.ACTIVE).stream()
                .anyMatch(price -> price.getDuration().equals(duration));
        log.info("Action.log.isValidDuration ended");
        return isValid;
    }

    private void validateNewDateForChange(AppointmentChangeDto changeDto, AppointmentEntity existingAppointment, DayOfWeek newDay) {
        log.info("Action.log.validateNewDateForChange started");

        if (newDay == DayOfWeek.SATURDAY || newDay == DayOfWeek.SUNDAY) {
            throw new TimeNotAllowedException("New date is outside working days");
        }

        if (!changeDto.getNewDate().isAfter(existingAppointment.getDay())) {
            throw new InvalidTimeException("New date must be after the current date");
        }

        if (existingAppointment.getStartTime().isBefore(LocalTime.now().plusHours(3))) {
            throw new CancellationException("Change failed because less than three hours remain before the appointment.");
        }

        log.info("Action.log.validateNewDateForChange ended");
    }

    private LocalDate calculateNextWeekDate(AppointmentRequest request, int week, int lastWeek) {
        log.info("Action.log.calculateNextWeekDate started");
        DayOfWeekEnum day = request.getDay();
        LocalDate today = LocalDate.now();
        int daysUntilNext = (day.getValue() - today.getDayOfWeek().getValue() + 7) % 7;

        if (daysUntilNext == 0) {
            daysUntilNext = 7;
        }

        // Add additional weeks if necessary
        if (week > 1) {
            daysUntilNext += (week - 1) * 7;
        }

        log.info("Action.log.calculateNextWeekDate ended");
        return today.plusDays(daysUntilNext);
    }

//    @Transactional
//    @Override
//    public void register(Long userId, Long specialistId, AppointmentRequestList appointmentRequestList) {
//
//        log.info("Action.log.register started for patient {}", userId);
//        SpecialistEntity specialist = specialistRepository.findByIdAndStatus(specialistId, Status.ACTIVE)
//                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));
//
//        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
//
//        Integer lastWeek = 0;
//        Integer week = 1;
//        for (int i = 0; i < appointmentRequestList.getAppointmentRequests().size(); i++) {
//
//            AppointmentRequest appointmentRequest = appointmentRequestList.getAppointmentRequests().get(i);
//            Integer duration = appointmentRequest.getDuration();
//            if (!durationCheck(duration)) throw new IllegalArgumentException("Invalid duration");
//
//            LocalDate nextWeekSameDay = getDateForNextWeek(appointmentRequest, week, lastWeek);
//
//            Integer dayInNum = appointmentRequest.getDay().getNumber();
//
//            ScheduleEntity specialistSchedule = scheduleRepository.findBySpecialistAndDay(specialist, dayInNum)
//                    .orElseThrow(() -> new EntityNotFoundException("Specialist not work at this time"));
//
//            if (!checkRequestWithSpecialistSchedule(specialistSchedule, appointmentRequest))
//                throw new IllegalArgumentException("Запрашиваемое время выходит за рамки рабочего времени специалиста.");
//
//            List<AppointmentEntity> reservedDays = appointmentRepository.
//                    findAllBySpecialistAndStatusAndDay(specialist, MeetingAndAppointmentStatus.SCHEDULED, nextWeekSameDay);
//
//            AppointmentEntity checkedEntity = checkWithReservedDays(nextWeekSameDay, appointmentRequest,
//                    reservedDays);
//            checkedEntity.setUser(user);
//            checkedEntity.setSpecialist(specialist);
//            checkedEntity.setWeek(appointmentRequestList.getWeek());
//            appointmentRepository.save(checkedEntity);
////            lastWeek = week;
////            if (week.equals(appointmentRequestList.getWeek())
////                    && appointmentRequestList.getAppointmentRequests().getLast().equals(appointmentRequest))
////                break;
//            lastWeek = week;
//            if (week.equals(appointmentRequestList.getWeek()) &&
//                    appointmentRequestList.getAppointmentRequests().getLast().equals(appointmentRequest)) break;
//
//            if (i == appointmentRequestList.getAppointmentRequests().size() - 1) {
//                week += 1;
//                i = -1;
//            }
//
//            log.info("Action.log.register ended for patient {}", userId);
//
//        }
//    }
//
//    @Override
//    public void cancel(Long userId, Long id) {
//        log.info("Action.log.cancel started for patient {}", userId);
//
//        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
//        AppointmentEntity appointmentEntity = appointmentRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Not found"));
//
//        if (appointmentEntity.getStatus().equals(MeetingAndAppointmentStatus.CANCELED))
//            throw new AlreadyException("Appointment already canceled");
//
//        LocalTime checkClock = LocalTime.now().minusHours(3);
//        if (appointmentEntity.getUser().equals(user)) {
//            if (appointmentEntity.getStartTime().isBefore(checkClock)) {
//                appointmentEntity.setStatus(MeetingAndAppointmentStatus.CANCELED);
//                appointmentRepository.save(appointmentEntity);
//                log.info("Action.log.cancel completed successfully for patient {}", userId);
//            } else {
//                throw new IllegalArgumentException("Cancel failed because there were less than three hours left before class.");
//            }
//        } else {
//            throw new IllegalArgumentException("Lesson not belong to this patient");
//        }
//
//    }
//
//    @Override
//    public void change(Long userId, Long id, AppointmentChangeDto appointmentChangeDto) {
//        log.info("Action.log.change started for patient {}", userId);
//
//        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found"));
//
//        AppointmentEntity appointmentEntity = appointmentRepository
//                .findByIdAndStatus(id, MeetingAndAppointmentStatus.SCHEDULED)
//                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
//        AppointmentRequest appointmentRequest = appointmentMapper.toRequestFromChange(appointmentChangeDto);
//        DayOfWeek isWorkDay = appointmentChangeDto.getNewDate().getDayOfWeek();
//        checkNewDateForChange(appointmentChangeDto, appointmentEntity, isWorkDay);
//        ScheduleEntity specialistSchedule = scheduleRepository.
//                findBySpecialistAndDay(appointmentEntity.getSpecialist(), isWorkDay.getValue())
//                .orElseThrow(() -> new EntityNotFoundException("Schedule not found"));
//        if (!checkRequestWithSpecialistSchedule(specialistSchedule, appointmentRequest))
//            throw new IllegalArgumentException("Запрашиваемое время выходит за ра��ки ра��очего времени специалиста.");
//
//        if (!durationCheck(appointmentChangeDto.getDuration()))
//            throw new IllegalArgumentException("Invalid duration"); // valid elemek olar?
//        List<AppointmentEntity> reservedDays = appointmentRepository
//                .findAllBySpecialistAndStatusAndDay(appointmentEntity.getSpecialist(),
//                        MeetingAndAppointmentStatus.SCHEDULED, appointmentChangeDto.getNewDate());
//
//        AppointmentEntity newAppointment = checkWithReservedDays(appointmentChangeDto.getNewDate(),
//                appointmentRequest, reservedDays);
//        appointmentRepository.save(newAppointment);
//
//    }
//
//    private AppointmentEntity checkWithReservedDays(LocalDate nextWeekSameDay,
//                                                    AppointmentRequest appointmentRequest,
//                                                    List<AppointmentEntity> reservedDays) {
//        log.info("Action.log.checkWithReservedDays started");
//        LocalTime requestStart = appointmentRequest.getStartTime();
//        LocalTime requestEnd = requestStart.plusMinutes(appointmentRequest.getDuration());
//
//        // Проверка на пересечение с обеденным перерывом
//        if (requestStart.isBefore(lunchEnd) && requestEnd.isAfter(lunchStart)) {
//            throw new IllegalArgumentException("Запрашиваемое время пересекается с перерывом на обед (13:00 - 14:00).");
//        }
//
//        // Проверка на пересечение с уже забронированным временем
//        for (AppointmentEntity appointment : reservedDays) {
//            LocalTime existingStartTime = appointment.getStartTime();
//            LocalTime existingEndTime = existingStartTime.plusMinutes(appointment.getDuration());
//
//            if (!(requestEnd.equals(existingStartTime) || requestStart.equals(existingEndTime) ||
//                    requestStart.isAfter(existingEndTime) || requestEnd.isBefore(existingStartTime))) {
//                throw new IllegalArgumentException("Время уже занято.");
//            }
//        }
//
//        // Создаем и возвращаем новый объект назначения
//        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
//                .day(nextWeekSameDay)
//                .startTime(requestStart)
//                .endTime(requestEnd)
//                .duration(appointmentRequest.getDuration())
//                .status(MeetingAndAppointmentStatus.SCHEDULED)
//                .build();
//
//        log.info("Action.log.checkWithReservedDays ended");
//        return appointmentEntity;
//    }
//
//    private boolean checkRequestWithSpecialistSchedule(ScheduleEntity specialistSchedule,
//                                                       AppointmentRequest appointmentRequest) {
//        log.info("Action.log.checkRequestWithSpecialistSchedule started");
//
//        LocalTime requestStartTime = appointmentRequest.getStartTime();
//        LocalTime requestEndTime = requestStartTime.plusMinutes(appointmentRequest.getDuration());
//
//        // Проверка, что время запроса в пределах рабочего времени
//        boolean isWithinWorkingHours = !requestStartTime.isBefore(specialistSchedule.getStartTime()) &&
//                !requestEndTime.isAfter(specialistSchedule.getEndTime());
//
//        log.info("Action.log.checkRequestWithSpecialistSchedule ended");
//        return isWithinWorkingHours;
//    }
//
//
//    private static LocalDate getDateForNextWeek(AppointmentRequest appointmentRequest, Integer week, Integer lastWeek) {
//        log.info("Action.log.getDateForNextWeek started");
//
//        DayOfWeekEnum requestedDayOfWeek = appointmentRequest.getDay();
//        LocalDate today = LocalDate.now();
//        int daysUntilTarget = (requestedDayOfWeek.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
//
//        if (daysUntilTarget == 0) {
//            daysUntilTarget = 7; // Если день недели совпадает с текущим, идем на следующую неделю
//        }
//
//        // Для следующих недель добавляем 7 дней за каждую неделю
//        if (week > 1) {
//            daysUntilTarget += (week - 1) * 7;
//        }
//
//        log.info("Action.log.getDateForNextWeek ended");
//        return today.plusDays(daysUntilTarget);
//    }
//
////    private static LocalDate getDateForNextWeek(AppointmentRequest appointmentRequest, Integer week, Integer lastWeek) {
////
////        log.info("Action.log.getDateForNextWeek started");
////
////        DayOfWeekEnum requestedDayOfWeek = appointmentRequest.getDay();
////
////        LocalDate today = LocalDate.now();
////
////        int daysUntilTarget = (requestedDayOfWeek.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
////
////        if (daysUntilTarget == 0) {
////            daysUntilTarget = 7;
////        }
//////        if (week>lastWeek) daysUntilTarget = daysUntilTarget * week;
////        if (week == 2) daysUntilTarget += 7;
////        if (week == 3) daysUntilTarget += 14;
////        if (week == 4) daysUntilTarget += 21;
////        log.info("Action.log.getDateForNextWeek ended");
////
////        return today.plusDays(daysUntilTarget);
////    }
//
//
//    private boolean durationCheck(Integer duration) {
//        log.info("Action.log.durationCheck started");
//        List<PriceEntity> priceEntities = priceRepository.findAllByStatus(Status.ACTIVE);
//        log.info("Action.log.durationCheck ended");
//        return priceEntities.stream()
//                .anyMatch(priceEntity -> priceEntity.getDuration().equals(duration));
//    }
//
//    private void checkNewDateForChange(AppointmentChangeDto appointmentChangeDto,
//                                       AppointmentEntity appointmentEntity, DayOfWeek dayOfWeek) {
//        log.info("Action.log.checkNewDateForChange started");
//        LocalTime checkTime = LocalTime.now().minusHours(3);
//
//        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
//            throw new IllegalArgumentException("Your request outside working days");
//
//        if (!appointmentChangeDto.getNewDate().isAfter(appointmentEntity.getDay()))
//            throw new IllegalArgumentException("New date must be after current date");
//
//        if (appointmentEntity.getStartTime().isBefore(checkTime))
//            throw new IllegalArgumentException
//                    ("Change failed because there were less than three hours left before class.");
//
//        log.info("Action.log.checkNewDateForChange started");
//    }
}

