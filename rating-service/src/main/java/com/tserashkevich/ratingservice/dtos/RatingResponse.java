package com.tserashkevich.ratingservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class RatingResponse {
    private final UUID id;
    private final String rideId;
    private final UUID sourceId;
    private final UUID targetId;
    private final Integer rating;
    private final String comment;
    private final LocalDateTime creationTime;
}
