package com.example.rehabilitationandintegration.validation.annotation;

import com.example.rehabilitationandintegration.validation.MeetingAppointmentStatusValidator;
import com.example.rehabilitationandintegration.validation.TaskStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaskStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskStatusConstraint {
    String message() default "INVALID TASK STATUS CHOICE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
