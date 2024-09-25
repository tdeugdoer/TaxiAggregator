package com.tserashkevich.rideservice.validators.validAnnotations;

import com.tserashkevich.rideservice.validators.PassengerExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PassengerExistValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PassengerExist {
    String message() default "Passenger not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}