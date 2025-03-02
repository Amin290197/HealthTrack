package com.example.rehabilitationandintegration.validation.annotation;

import com.example.rehabilitationandintegration.validation.StatusValidator;
import com.example.rehabilitationandintegration.validation.TaskStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusConstraint {
    String message() default "INVALID STATUS CHOICE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
