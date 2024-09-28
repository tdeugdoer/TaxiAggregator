package com.tserashkevich.ratingservice.mappers;

import com.tserashkevich.ratingservice.dtos.Feedback;
import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.ratingservice.models.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    Rating toModel(RatingRequest ratingRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    Rating toModel(RatingCreateEvent ratingCreateEvent);

    RatingResponse toRatingResponse(Rating rating);

    List<RatingResponse> toRatingResponses(List<Rating> ratings);

    List<Feedback> toFeedbacks(List<Rating> ratings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    void updateModel(@MappingTarget Rating rating, RatingRequest ratingRequest);
}
