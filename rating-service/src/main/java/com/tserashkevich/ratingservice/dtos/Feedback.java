package com.tserashkevich.ratingservice.dtos;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Feedback {
    private final Integer rating;
    private final String comment;
    private final LocalDateTime creationTime;
}