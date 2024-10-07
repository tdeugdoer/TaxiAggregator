package com.tserashkevich.rideservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class RideResponse {
    private final String id;
    private final UUID driverId;
    private final UUID passengerId;
    private final Long carId;
    private final AddressResponse startAddress;
    private final AddressResponse endAddress;
    private final Integer distance;
    private final TimeResponse time;
    private final String status;
}
