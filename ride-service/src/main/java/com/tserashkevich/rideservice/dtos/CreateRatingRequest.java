package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.utils.ValidationList;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRatingRequest {
    private final String rideId;
    private final String comment;

    @NotNull(message = ValidationList.RATING_REQUIRED)
    @Min(value = 0, message = ValidationList.LESS_ZERO)
    @Max(value = 10, message = ValidationList.MORE_TEN)
    private final Integer rating;
}
