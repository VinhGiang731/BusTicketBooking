package com.project.busticket.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeFormatValidator.class)
public @interface ValidationTime {
    String message() default "Invalid format (HH:mm dd/MM/yyyy) and should not overlap departurTime";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
