package com.tserashkevich.driverservice.validators;

import com.tserashkevich.driverservice.services.CarService;
import com.tserashkevich.driverservice.validators.validAnnotations.ValidCarNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarNumberValidator implements ConstraintValidator<ValidCarNumber, String> {
    private final CarService carService;

    @Override
    public boolean isValid(String carNumber, ConstraintValidatorContext context) {
        return !carService.existByCarNumber(carNumber);
    }
}
