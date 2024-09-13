package com.tserashkevich.ratingservice.mappers;

import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.models.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {
    @Mapping(target = "id", ignore = true)
    Rating toModel(RatingRequest ratingRequest);
    RatingResponse toResponse(Rating rating);
    List<RatingResponse> toResponses(List<Rating> ratings);
    @Mapping(target = "id", ignore = true)
    void updateModel(@MappingTarget Rating rating, RatingRequest ratingRequest);
}
