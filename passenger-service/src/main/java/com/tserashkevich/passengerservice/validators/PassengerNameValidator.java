package com.tserashkevich.passengerservice.validators;

import com.tserashkevich.passengerservice.services.PassengerService;
import com.tserashkevich.passengerservice.validators.validAnnotations.ValidPassengerName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PassengerNameValidator implements ConstraintValidator<ValidPassengerName, String> {
    private final PassengerService passengerService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !passengerService.existByName(name);
    }
}
