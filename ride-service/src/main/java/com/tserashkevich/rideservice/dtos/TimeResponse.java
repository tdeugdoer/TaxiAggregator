package com.tserashkevich.rideservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TimeResponse {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}
