package com.tserashkevich.ratingservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RatingRequest {
    private final String sourceId;
    private final String targetId;
    private final String comment;
}
