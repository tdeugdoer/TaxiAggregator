package com.tserashkevich.rideservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CreateRideResponse {
    private final String id;
    private final UUID passengerId;
    private final AddressResponse startAddress;
    private final AddressResponse endAddress;
    private final Integer distance;
    private final LocalDateTime startTime;
    private final String status;
}
