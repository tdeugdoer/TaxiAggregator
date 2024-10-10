package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.utils.PatternList;
import com.tserashkevich.rideservice.utils.ValidationList;
import com.tserashkevich.rideservice.validators.validAnnotations.PassengerExist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRideRequest {
    @PassengerExist(message = ValidationList.PASSENGER_NOT_EXIST)
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
