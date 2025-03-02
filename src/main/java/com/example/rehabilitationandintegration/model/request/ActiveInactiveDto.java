package com.example.rehabilitationandintegration.model.request;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.validation.annotation.StatusConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ActiveInactiveDto {
    @Enumerated(EnumType.STRING)
    @StatusConstraint(enumClass = Status.class)
    private Status status;
}
