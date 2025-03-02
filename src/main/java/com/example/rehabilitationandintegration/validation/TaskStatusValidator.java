package com.example.rehabilitationandintegration.validation;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.enums.TaskStatus;
import com.example.rehabilitationandintegration.validation.annotation.MeetingAppointmentStatusConstraint;
import com.example.rehabilitationandintegration.validation.annotation.TaskStatusConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class TaskStatusValidator
        implements ConstraintValidator<TaskStatusConstraint, TaskStatus> {

    private EnumSet<TaskStatus> validStatus;

    @Override
    public void initialize(TaskStatusConstraint constraintAnnotation) {
        validStatus = EnumSet.allOf(TaskStatus.class);
    }

    @Override
    public boolean isValid(TaskStatus status, ConstraintValidatorContext context) {
        return status != null && validStatus.contains(status);
    }
}
