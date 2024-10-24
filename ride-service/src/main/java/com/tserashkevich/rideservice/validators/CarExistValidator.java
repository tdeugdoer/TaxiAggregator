package com.tserashkevich.rideservice.validators;

import com.tserashkevich.rideservice.feing.CarFeignClient;
import com.tserashkevich.rideservice.validators.validAnnotations.CarExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarExistValidator implements ConstraintValidator<CarExist, Long> {
    private final CarFeignClient carFeignClient;

    @Override
    public boolean isValid(Long carId, ConstraintValidatorContext context) {
        return carFeignClient.getExistCar(carId);
    }
}
