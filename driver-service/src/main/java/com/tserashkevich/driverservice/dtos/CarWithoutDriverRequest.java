package com.tserashkevich.driverservice.dtos;

import com.tserashkevich.driverservice.utils.PatternList;
import com.tserashkevich.driverservice.utils.ValidationList;
import com.tserashkevich.driverservice.validators.validAnnotations.ValidCarNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CarWithoutDriverRequest {
    @ValidCarNumber(message = ValidationList.CAR_NUMBER_NUMBER_ALREADY_EXIST)
    @NotBlank(message = ValidationList.CAR_NUMBER_REQUIRED)
    @Size(max = 10, message = ValidationList.WRONG_MAX_CAR_NUMBER_LENGTH)
    private final String number;

    @NotBlank(message = ValidationList.CAR_BRAND_REQUIRED)
    @Size(max = 10, message = ValidationList.WRONG_CAR_BRAND_LENGTH)
    private final String brand;

    @NotBlank(message = ValidationList.CAR_MODEL_REQUIRED)
    @Size(max = 10, message = ValidationList.WRONG_CAR_MODEL_LENGTH)
    private final String model;

    @NotBlank(message = ValidationList.CAR_COLOR_REQUIRED)
    @Pattern(regexp = PatternList.COLOR_PATTERN, message = ValidationList.WRONG_COLOR)
    private final String color;
}
