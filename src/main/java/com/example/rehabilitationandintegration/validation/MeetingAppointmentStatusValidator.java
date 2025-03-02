package com.example.rehabilitationandintegration.validation;

import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import com.example.rehabilitationandintegration.validation.annotation.MeetingAppointmentStatusConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class MeetingAppointmentStatusValidator
        implements ConstraintValidator<MeetingAppointmentStatusConstraint, MeetingAndAppointmentStatus> {

    private EnumSet<MeetingAndAppointmentStatus> validStatus;

    @Override
    public void initialize(MeetingAppointmentStatusConstraint constraintAnnotation) {
        validStatus = EnumSet.allOf(MeetingAndAppointmentStatus.class);
    }

    @Override
    public boolean isValid(MeetingAndAppointmentStatus status, ConstraintValidatorContext context) {
        return status != null && validStatus.contains(status);
    }
}
