package com.example.rehabilitationandintegration.validation.annotation;

import com.example.rehabilitationandintegration.validation.LanguageValidator;
import com.example.rehabilitationandintegration.validation.MeetingAppointmentStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MeetingAppointmentStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MeetingAppointmentStatusConstraint {
    String message() default "INVALID MEETING OR APPOINTMENT STATUS CHOICE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
