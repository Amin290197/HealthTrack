package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AppointmentRequestList {
    @Size(max = 5, min = 1)
    private List<AppointmentRequest> appointmentRequests;

    @NotNull(message = "DURATION CANNOT BE NULL")
    @Min(value = 1, message = "DURATION MUST BE AT LEAST 1 MINUTE")
    private Integer week;

    private AccountRequestDto accountRequestDto;
}
