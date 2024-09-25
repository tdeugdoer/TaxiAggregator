package com.tserashkevich.ratingservice.service;

import com.tserashkevich.ratingservice.dtos.*;

import java.util.List;
import java.util.UUID;

public interface RatingService {
    RatingResponse create(RatingRequest ratingRequest);

    RatingResponse update(UUID ratingId, RatingRequest ratingRequest);

    void delete(UUID ratingId);

    PageResponse<RatingResponse> findAll(FindAllParams findAllParams);

    RatingResponse findById(UUID ratingId);

    Double findAvgRating(UUID targetId);

    List<Feedback> findFeedbacks(UUID targetId);
}