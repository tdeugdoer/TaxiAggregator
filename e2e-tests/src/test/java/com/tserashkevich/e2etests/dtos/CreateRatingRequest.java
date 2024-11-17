package com.tserashkevich.e2etests.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRatingRequest {
    private final String rideId;
    private final String comment;
    private final Integer rating;
}
