package com.tserashkevich.driverservice.validators.validAnnotations;

import com.tserashkevich.driverservice.validators.CarNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {CarNumberValidator.class})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCarNumber {
    String message() default "Car number already exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
