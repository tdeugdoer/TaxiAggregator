package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.utils.PatternList;
import com.tserashkevich.rideservice.utils.ValidationList;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DriverIdRequest {
    @NotBlank(message = ValidationList.DRIVER_ID_REQUIRED)
    @Pattern(regexp = PatternList.UUID_PATTERN, message = ValidationList.WRONG_UUID_FORMAT)
    private final String driverId;
}
