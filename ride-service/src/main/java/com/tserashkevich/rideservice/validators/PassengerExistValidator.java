package com.tserashkevich.rideservice.validators;

import com.tserashkevich.rideservice.feing.PassengerFeignClient;
import com.tserashkevich.rideservice.validators.validAnnotations.PassengerExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PassengerExistValidator implements ConstraintValidator<PassengerExist, String> {
    private final PassengerFeignClient passengerFeignClient;
    @Override
    public boolean isValid(String passengerId, ConstraintValidatorContext context) {
        return passengerFeignClient.getExistPassenger(UUID.fromString(passengerId));
    }
}
