package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentChangeDto {

    @NotNull(message = "NEW DATE CANNOT BE NULL")
    @Future(message = "NEW DATE MUST BE IN THE FUTURE")
    private LocalDate newDate;

    @NotNull(message = "START TIME CANNOT BE NULL")
    private LocalTime start;

    @NotNull(message = "DURATION CANNOT BE NULL")
    @Min(value = 30, message = "DURATION MUST BE AT LEAST 30 MINUTE")
    private Integer duration;
}
