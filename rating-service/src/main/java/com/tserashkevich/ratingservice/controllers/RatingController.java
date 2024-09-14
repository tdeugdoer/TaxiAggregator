package com.tserashkevich.ratingservice.controllers;

import com.tserashkevich.ratingservice.dtos.PageResponse;
import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.service.RatingService;
import com.tserashkevich.ratingservice.utils.RatingSortList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/ratings")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse createRating(@Valid @RequestBody RatingRequest ratingRequest) {
        return ratingService.create(ratingRequest);
    }

    @PutMapping("/{ratingId}")
    public RatingResponse updateRating(@PathVariable UUID ratingId, @Valid @RequestBody RatingRequest ratingRequest) {
        return ratingService.update(ratingId, ratingRequest);
    }

    @DeleteMapping("/{ratingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable UUID ratingId) {
        ratingService.delete(ratingId);
    }

    @GetMapping
    public PageResponse<RatingResponse> findAllRatings(@RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                       @RequestParam(defaultValue = "ID_ASC") RatingSortList sort,
                                                       @RequestParam(required = false) UUID sourceId,
                                                       @RequestParam(required = false) UUID targetId,
                                                       @RequestParam(required = false) Integer rating) {
        return ratingService.findAll(limit, sort, sourceId, targetId, rating);
    }

    @GetMapping("/{ratingId}")
    public RatingResponse findRatingById(@PathVariable UUID ratingId) {
        return ratingService.findById(ratingId);
    }
}
