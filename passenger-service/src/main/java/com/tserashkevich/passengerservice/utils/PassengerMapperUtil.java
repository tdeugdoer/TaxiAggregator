package com.tserashkevich.passengerservice.utils;

import com.tserashkevich.passengerservice.feing.RatingFeingClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Named("PassengerMapperUtil")
@RequiredArgsConstructor
@Component
public class PassengerMapperUtil {
    private final RatingFeingClient ratingFeingClient;

    @Named("getAvgRating")
    public Double getAvgRating(UUID passengerId) {
        return ratingFeingClient
                .findTargetAvgRating(passengerId);
    }
}
