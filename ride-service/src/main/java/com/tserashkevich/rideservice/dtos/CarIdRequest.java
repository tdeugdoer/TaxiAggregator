package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.utils.ValidationList;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarIdRequest {
    @NotBlank(message = ValidationList.CAR_ID_REQUIRED)
    @Min(value = 1, message = ValidationList.NEGATIVE_VALUE)
    private final Long carId;
}
