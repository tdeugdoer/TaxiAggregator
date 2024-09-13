package com.tserashkevich.rideservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
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
