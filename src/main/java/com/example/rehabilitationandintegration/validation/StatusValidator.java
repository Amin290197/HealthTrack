package com.example.rehabilitationandintegration.validation;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.enums.TaskStatus;
import com.example.rehabilitationandintegration.validation.annotation.StatusConstraint;
import com.example.rehabilitationandintegration.validation.annotation.TaskStatusConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class StatusValidator
        implements ConstraintValidator<StatusConstraint, Status> {

    private EnumSet<Status> validStatus;

    @Override
    public void initialize(StatusConstraint constraintAnnotation) {
        validStatus = EnumSet.allOf(Status.class);
    }

    @Override
    public boolean isValid(Status status, ConstraintValidatorContext context) {
        return status != null && validStatus.contains(status);
    }
}
