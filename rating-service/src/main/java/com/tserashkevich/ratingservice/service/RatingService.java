package com.tserashkevich.ratingservice.service;

import com.tserashkevich.ratingservice.dtos.PageResponse;
import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.utils.RatingSortList;

import java.util.UUID;

public interface RatingService {
    RatingResponse create(RatingRequest ratingRequest);
    RatingResponse update(UUID ratingId, RatingRequest ratingRequest);
    void delete(UUID ratingId);
    PageResponse<RatingResponse> findAll(int limit, RatingSortList sort, UUID sourceId, UUID targetId, Integer rating);
    RatingResponse findById(UUID ratingId);
}