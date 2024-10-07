package com.tserashkevich.rideservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CreateRideResponse {
    private final String id;
    private final UUID passengerId;
    private final AddressResponse startAddress;
    private final AddressResponse endAddress;
    private final Integer distance;
    private final LocalDateTime startTime;
    private final String status;
}
