package com.example.rehabilitationandintegration.validation;

import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.validation.annotation.LanguageConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class LanguageValidator implements ConstraintValidator<LanguageConstraint, Language> {

    private EnumSet<Language> validLanguages;

    @Override
    public void initialize(LanguageConstraint constraintAnnotation) {
        validLanguages = EnumSet.allOf(Language.class);
    }

    @Override
    public boolean isValid(Language language, ConstraintValidatorContext context) {
        return language != null && validLanguages.contains(language);
    }
}
