package com.tserashkevich.rideservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TimeResponse {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
}
