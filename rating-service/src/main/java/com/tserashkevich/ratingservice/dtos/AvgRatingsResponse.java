package com.tserashkevich.ratingservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AvgRatingsResponse {
    private final Double avgRating;
}
