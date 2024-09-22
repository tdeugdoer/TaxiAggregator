package com.tserashkevich.rideservice.validators.validAnnotations;

import com.tserashkevich.rideservice.validators.CarExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {CarExistValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarExist {
    String message() default "Car not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}