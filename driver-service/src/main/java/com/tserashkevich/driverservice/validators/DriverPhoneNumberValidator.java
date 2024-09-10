package com.tserashkevich.driverservice.validators;

import com.tserashkevich.driverservice.services.DriverService;
import com.tserashkevich.driverservice.validators.validAnnotations.ValidDriverPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DriverPhoneNumberValidator implements ConstraintValidator<ValidDriverPhoneNumber, String> {
    private final DriverService driverService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return !driverService.existByPhoneNumber(phoneNumber);
    }
}
