package com.tserashkevich.rideservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateRatingRequest {
    private final String rideId;
    private final String comment;
    private final Integer rating;
}
