package com.tserashkevich.rideservice.validators;

import com.tserashkevich.rideservice.feing.DriverFeignClient;
import com.tserashkevich.rideservice.validators.validAnnotations.DriverExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DriverExistValidator implements ConstraintValidator<DriverExist, String> {
    private final DriverFeignClient driverFeignClient;
    @Override
    public boolean isValid(String driverId, ConstraintValidatorContext context) {
        return driverFeignClient.getExistDriver(UUID.fromString(driverId)).getExist();
    }
}
