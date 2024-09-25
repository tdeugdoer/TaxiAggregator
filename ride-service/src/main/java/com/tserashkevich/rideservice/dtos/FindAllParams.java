package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.models.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class FindAllParams {
    private final Integer page;
    private final Integer limit;
    private final Sort sort;
    private final UUID driverId;
    private final UUID passengerId;
    private final Long carId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Integer minDistance;
    private final Integer maxDistance;
    private final Status status;
}