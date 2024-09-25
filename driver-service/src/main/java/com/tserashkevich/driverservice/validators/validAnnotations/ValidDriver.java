package com.tserashkevich.driverservice.validators.validAnnotations;

import com.tserashkevich.driverservice.validators.DriverValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {DriverValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDriver {
    String message() default "Driver already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
