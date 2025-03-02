package com.example.rehabilitationandintegration.model.request;

import com.example.rehabilitationandintegration.enums.DayOfWeekEnum;
import com.example.rehabilitationandintegration.validation.annotation.DayOfWeekConstraint;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class AppointmentRequest {

    @NotNull(message = "DAY CANNOT BE NULL")
    @DayOfWeekConstraint(enumClass = DayOfWeekEnum.class)
    private DayOfWeekEnum day;

    @NotNull(message = "NEW DATE CANNOT BE NULL")
    @Future(message = "NEW DATE MUST BE IN THE FUTURE")
    private LocalTime startTime;

    @NotNull(message = "DURATION CANNOT BE NULL")
    @Min(value = 30, message = "DURATION MUST BE AT LEAST 30 MINUTE")
    private Integer duration;
}
