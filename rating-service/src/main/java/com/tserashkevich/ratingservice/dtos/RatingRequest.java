package com.tserashkevich.ratingservice.dtos;

import com.tserashkevich.ratingservice.utils.PatternList;
import com.tserashkevich.ratingservice.utils.ValidationList;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RatingRequest {
    @NotBlank(message = ValidationList.RIDE_ID_REQUIRED)
    private final String rideId;

    @NotBlank(message = ValidationList.SOURCE_ID_REQUIRED)
    @Pattern(regexp = PatternList.UUID_PATTERN, message = ValidationList.WRONG_UUID_FORMAT)
    private final String sourceId;

    @NotBlank(message = ValidationList.TARGET_ID_REQUIRED)
    @Pattern(regexp = PatternList.UUID_PATTERN, message = ValidationList.WRONG_UUID_FORMAT)
    private final String targetId;

    @NotNull(message = ValidationList.RATING_REQUIRED)
    @Min(value = 0, message = ValidationList.LESS_ZERO)
    @Max(value = 10, message = ValidationList.MORE_TEN)
    private final Integer rating;

    private final String comment;
}
