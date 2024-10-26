package com.tserashkevich.e2etests.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRideRequest {
    private final String passengerId;
    private final String startGeoPoint;
    private final String endGeoPoint;
}
