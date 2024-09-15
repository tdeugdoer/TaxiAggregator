package com.tserashkevich.driverservice.validators;

import com.tserashkevich.driverservice.services.DriverService;
import com.tserashkevich.driverservice.validators.validAnnotations.ValidDriver;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DriverValidator implements ConstraintValidator<ValidDriver, String> {
    private final DriverService driverService;

    @Override
    public boolean isValid(String driverId, ConstraintValidatorContext context) {
        return driverService.existById(UUID.fromString(driverId));
    }
}
