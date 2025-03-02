package com.example.rehabilitationandintegration.validation;

import com.example.rehabilitationandintegration.enums.Gender;
import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.validation.annotation.GenderConstraint;
import com.example.rehabilitationandintegration.validation.annotation.LanguageConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class GenderValidator implements ConstraintValidator<GenderConstraint, Gender> {
    private EnumSet<Gender> validGenders;

    @Override
    public void initialize(GenderConstraint constraintAnnotation) {
        validGenders = EnumSet.allOf(Gender.class);
    }

    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext context) {
        return gender != null && validGenders.contains(gender);
    }
}
