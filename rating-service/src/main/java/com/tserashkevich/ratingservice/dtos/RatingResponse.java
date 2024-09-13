package com.tserashkevich.ratingservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class RatingResponse {
    private final UUID id;
    private final UUID sourceId;
    private final UUID targetId;
    private final String comment;
}
