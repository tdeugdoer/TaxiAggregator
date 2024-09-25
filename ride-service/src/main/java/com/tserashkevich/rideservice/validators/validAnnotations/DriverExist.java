package com.tserashkevich.rideservice.validators.validAnnotations;

import com.tserashkevich.rideservice.validators.DriverExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {DriverExistValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DriverExist {
    String message() default "Driver not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}