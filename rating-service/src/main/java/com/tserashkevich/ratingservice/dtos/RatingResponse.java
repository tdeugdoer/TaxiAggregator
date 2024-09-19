package com.tserashkevich.ratingservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class RatingResponse {
    private final UUID id;
    private final UUID sourceId;
    private final UUID targetId;
    private final Integer rating;
    private final String comment;
    private final LocalDateTime creationTime;
}
