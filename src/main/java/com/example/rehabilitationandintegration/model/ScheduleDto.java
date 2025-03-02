package com.example.rehabilitationandintegration.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleDto {
    private Long id;

    @NotNull(message = "DAY CANNOT BE NULL")
    @Positive(message = "DAY MUST BE POSITIVE")
    private Integer day;

    @NotNull(message = "START TIME CANNOT BE NULL")
    private LocalTime startTime;

    @NotNull(message = "END TIME CANNOT BE NULL")
    private LocalTime endTime;


    public ScheduleDto(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
