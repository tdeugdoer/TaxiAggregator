package com.tserashkevich.driverservice.utils;

import com.tserashkevich.driverservice.feign.RatingFeignClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Named("DriverMapperUtil")
@RequiredArgsConstructor
@Component
public class DriverMapperUtil {
    private final RatingFeignClient ratingFeingClient;

    @Named("getAvgRating")
    public Double getAvgRating(UUID passengerId) {
        return ratingFeingClient
                .findTargetAvgRating(passengerId);
    }
}
