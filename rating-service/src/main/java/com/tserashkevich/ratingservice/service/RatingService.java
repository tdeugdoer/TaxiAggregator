package com.tserashkevich.ratingservice.service;

import com.tserashkevich.ratingservice.dtos.PageResponse;
import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface RatingService {
    RatingResponse create(RatingRequest ratingRequest);
    RatingResponse update(UUID ratingId, RatingRequest ratingRequest);
    void delete(UUID ratingId);
    PageResponse<RatingResponse> findAll(int page, int limit, Sort sort, UUID sourceId, UUID targerId, String comment);
    RatingResponse findById(UUID ratingId);
}