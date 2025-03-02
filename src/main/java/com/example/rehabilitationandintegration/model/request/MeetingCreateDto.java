package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MeetingCreateDto {
    @NotNull(message = "ID MUST NOT BE NULL")
    @Positive(message = "ID MUST BE A POSITIVE NUMBER")
    private Long id;

    @NotNull(message = "DATE MUST NOT BE NULL")
    @Future(message = "DATE MUST BE IN THE FUTURE")
    private LocalDate date;

    @NotNull(message = "TIME MUST NOT BE NULL")
    private LocalTime time;
}
