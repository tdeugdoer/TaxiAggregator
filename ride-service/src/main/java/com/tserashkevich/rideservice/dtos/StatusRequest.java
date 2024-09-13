package com.tserashkevich.rideservice.dtos;

import com.tserashkevich.rideservice.utils.PatternList;
import com.tserashkevich.rideservice.utils.ValidationList;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatusRequest {
    @NotBlank(message = ValidationList.STATUS_REQUIRED)
    @Pattern(regexp = PatternList.STATUS_PATTERN, message = ValidationList.WRONG_STATUS)
    private final String status;
}
