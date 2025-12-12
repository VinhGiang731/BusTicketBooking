package com.project.busticket.validation;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateTimeFormatValidator implements ConstraintValidator<ValidationTime, LocalDateTime> {

    @Override
    public void initialize(ValidationTime constraintAnnotation) {
        // TODO Auto-generated method stub
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime arg0, ConstraintValidatorContext arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}
