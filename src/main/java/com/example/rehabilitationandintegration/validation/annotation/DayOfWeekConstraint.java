package com.example.rehabilitationandintegration.validation.annotation;

import com.example.rehabilitationandintegration.validation.DayOfWeekValidator;
import com.example.rehabilitationandintegration.validation.LanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DayOfWeekValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DayOfWeekConstraint {
    String message() default "INVALID DAY";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
