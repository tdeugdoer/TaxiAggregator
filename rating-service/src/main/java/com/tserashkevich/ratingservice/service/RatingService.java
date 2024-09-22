package com.tserashkevich.ratingservice.service;

import com.tserashkevich.ratingservice.dtos.*;

import java.util.UUID;

public interface RatingService {
    RatingResponse create(RatingRequest ratingRequest);
    RatingResponse update(UUID ratingId, RatingRequest ratingRequest);
    void delete(UUID ratingId);
    PageResponse<RatingResponse> findAll(FindAllParams findAllParams);
    RatingResponse findById(UUID ratingId);
    AvgRatingsResponse findAvgRating(UUID targetId);
    FeedbackResponse findFeedbacks(UUID targetId);
}