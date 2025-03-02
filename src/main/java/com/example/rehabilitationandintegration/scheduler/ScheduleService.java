package com.example.rehabilitationandintegration.scheduler;

import com.example.rehabilitationandintegration.service.AppointmentService;
import com.example.rehabilitationandintegration.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final AppointmentService appointmentService;
    private final MeetingService meetingService;

    @Scheduled(cron = "0 10 0 ? * TUE-SAT")
    public void completeAppointments() {
        appointmentService.complete();
    }

    @Scheduled(cron = "0 10 0 ? * TUE-SAT")
    public void completeMeetings() {
        meetingService.complete();
    }

}
