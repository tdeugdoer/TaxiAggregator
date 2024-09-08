package com.tserashkevich.passengerservice.validators.validAnnotations;

import com.tserashkevich.passengerservice.validators.PassengerPhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {PassengerPhoneNumberValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassengerPhoneNumber {
    String message() default "Passenger phone number already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}