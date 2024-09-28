package com.tserashkevich.passengerservice.utils;

import com.tserashkevich.passengerservice.feing.RatingFeignClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Named("PassengerMapperUtil")
@RequiredArgsConstructor
@Component
public class PassengerMapperUtil {
    private final RatingFeignClient ratingFeignClient;

    @Named("getAvgRating")
    public Double getAvgRating(UUID passengerId) {
        return ratingFeignClient
                .findTargetAvgRating(passengerId);
    }
}
