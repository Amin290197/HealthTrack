package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.validation.annotation.MeetingAppointmentStatusConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MeetingDto {
    private Long id;
    @NotNull(message = "DATE CANNOT BE NULL")
    private LocalDate date;

    @NotNull(message = "TIME CANNOT BE NULL")
    private LocalTime time;

    @NotNull(message = "CREATE DATE CANNOT BE NULL")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @MeetingAppointmentStatusConstraint(enumClass = MeetingAndAppointmentStatus.class)
    private MeetingAndAppointmentStatus status;
}
