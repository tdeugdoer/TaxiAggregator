package com.tserashkevich.ratingservice.dtos;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Feedback {
    private final Integer rating;
    private final String comment;
    private final LocalDateTime creationTime;
}