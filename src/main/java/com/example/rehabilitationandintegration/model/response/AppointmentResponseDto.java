package com.example.rehabilitationandintegration.model.response;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentResponseDto {
    private Long id;

    @NotNull(message = "DAY CANNOT BE NULL")
    private LocalDate day;

    @NotNull(message = "START TIME CANNOT BE NULL")
    private LocalTime startTime;

    @NotNull(message = "DURATION CANNOT BE NULL")
    @Min(value = 30, message = "DURATION MUST BE AT LEAST 30 MINUTE")
    private Integer duration;
}
