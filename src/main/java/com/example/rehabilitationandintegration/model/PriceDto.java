package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.PriceCurrency;
import com.example.rehabilitationandintegration.enums.PriceMinute;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.validation.annotation.StatusConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class PriceDto {
    private Long id;

    private Double price;

    @Enumerated(EnumType.STRING)
    private PriceCurrency currency;

    @NotNull(message = "DURATION CANNOT BE NULL")
    @Min(value = 30, message = "DURATION MUST BE AT LEAST 30 MINUTE")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private PriceMinute minute;

    @Enumerated(EnumType.STRING)
    @StatusConstraint(enumClass = Status.class)
    private Status status;
}
