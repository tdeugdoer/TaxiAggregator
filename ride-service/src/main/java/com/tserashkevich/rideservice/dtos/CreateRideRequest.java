package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.utils.PatternList;
import com.tserashkevich.rideservice.utils.ValidationList;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateRideRequest {
    @NotBlank(message = ValidationList.PASSENGER_ID_REQUIRED)
    @Pattern(regexp = PatternList.UUID_PATTERN, message = ValidationList.WRONG_UUID_FORMAT)
    private final String passengerId;

    @NotBlank(message = ValidationList.START_ADDRESS_REQUIRED)
    @Pattern(regexp = PatternList.GEO_PATTERN, message = ValidationList.WRONG_GEO_POINT_FORMAT)
    private final String startGeoPoint;

    @NotBlank(message = ValidationList.END_ADDRESS_REQUIRED)
    @Pattern(regexp = PatternList.GEO_PATTERN, message = ValidationList.WRONG_GEO_POINT_FORMAT)
    private final String endGeoPoint;
}
