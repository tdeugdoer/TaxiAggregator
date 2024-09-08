package com.tserashkevich.passengerservice.validators.validAnnotations;

import com.tserashkevich.passengerservice.validators.PassengerNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PassengerNameValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassengerName {
    String message() default "Passenger name already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}