package com.example.rehabilitationandintegration.validation;

import com.example.rehabilitationandintegration.enums.DayOfWeekEnum;
import com.example.rehabilitationandintegration.validation.annotation.DayOfWeekConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class DayOfWeekValidator implements ConstraintValidator<DayOfWeekConstraint, DayOfWeekEnum> {
    private EnumSet<DayOfWeekEnum> validDays;

    @Override
    public void initialize(DayOfWeekConstraint constraintAnnotation) {
        validDays = EnumSet.allOf(DayOfWeekEnum.class);
    }

    @Override
    public boolean isValid(DayOfWeekEnum day, ConstraintValidatorContext context) {
        return day != null && validDays.contains(day);
    }
}
