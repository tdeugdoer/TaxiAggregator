package com.tserashkevich.passengerservice.validators;

import com.tserashkevich.passengerservice.services.PassengerService;
import com.tserashkevich.passengerservice.validators.validAnnotations.ValidPassengerPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PassengerPhoneNumberValidator implements ConstraintValidator<ValidPassengerPhoneNumber, String> {
    private final PassengerService passengerService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return !passengerService.existByPhoneNumber(phoneNumber);
    }
}
