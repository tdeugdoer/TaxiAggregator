package com.tserashkevich.ratingservice.utils;

import com.tserashkevich.ratingservice.dtos.*;
import com.tserashkevich.ratingservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.ratingservice.models.Rating;
import lombok.experimental.UtilityClass;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class TestUtil {
    public final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public final UUID NON_EXISTING_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public final String RIDE_ID = "507f1f77bcf86cd799439011";
    public final String SECOND_RIDE_ID = "6592008029c8c3e4dc76256c";
    public final String NON_EXISTING_RIDE_ID = "1052008029c8c3e4dc76877m";
    public final UUID SOURCE_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public final UUID SECOND_SOURCE_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public final UUID TARGET_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");
    public final UUID SECOND_TARGET_ID = UUID.fromString("66666666-6666-6666-6666-666666666666");
    public final Integer RATING = 5;
    public final Integer SECOND_RATING = 8;
    public final Double DOUBLE_RATING = 5.0;
    public final String COMMENT = "Комментарий 1";
    public final String SECOND_COMMENT = "Комментарий 2";
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final RatingSortList SORT = RatingSortList.ID_ASC;
    public final String SORT_NAME = "ID_ASC";
    public final String RATING_NOT_FOUND_MESSAGE = "Рейтинг не найден";
    public final String RATING_EXIST_MESSAGE = "Пользователь уже оценил эту поездку";
    public final String DEFAULT_PATH = "/api/v1/ratings";
    private final LocalDateTime CREATION_TIME = LocalDateTime.of(2024, 1, 1, 13, 34, 34);
    private final LocalDateTime SECOND_CREATION_TIME = LocalDateTime.of(2024, 5, 23, 20, 12, 34);

    public Rating getRating() {
        return Rating.builder()
                .id(ID)
                .sourceId(SOURCE_ID)
                .targetId(TARGET_ID)
                .rideId(RIDE_ID)
                .rating(RATING)
                .comment(COMMENT)
                .creationTime(CREATION_TIME)
                .build();
    }

    public Rating getSecondRating() {
        return Rating.builder()
                .id(SECOND_ID)
                .sourceId(SECOND_SOURCE_ID)
                .targetId(SECOND_TARGET_ID)
                .rideId(SECOND_RIDE_ID)
                .rating(SECOND_RATING)
                .comment(SECOND_COMMENT)
                .creationTime(SECOND_CREATION_TIME)
                .build();
    }

    public Rating getNonSavedRating() {
        return Rating.builder()
                .sourceId(SOURCE_ID)
                .targetId(TARGET_ID)
                .rideId(RIDE_ID)
                .rating(RATING)
                .comment(COMMENT)
                .build();
    }

    public List<Rating> getRatings() {
        return List.of(
                getRating(),
                getSecondRating()
        );
    }

    public RatingRequest getNonExistingRatingRequest() {
        return RatingRequest.builder()
                .sourceId(TARGET_ID.toString())
                .targetId(SOURCE_ID.toString())
                .rideId(RIDE_ID)
                .rating(RATING)
                .comment(COMMENT)
                .build();
    }

    public RatingRequest getRatingRequest() {
        return RatingRequest.builder()
                .sourceId(SOURCE_ID.toString())
                .targetId(TARGET_ID.toString())
                .rideId(RIDE_ID)
                .rating(RATING)
                .comment(COMMENT)
                .build();
    }

    public RatingRequest getRatingRequest(String sourceId, String rideId) {
        return RatingRequest.builder()
                .sourceId(sourceId)
                .targetId(TARGET_ID.toString())
                .rideId(rideId)
                .rating(RATING)
                .comment(COMMENT)
                .build();
    }

    public RatingRequest getEditRatingRequest() {
        return RatingRequest.builder()
                .sourceId(SECOND_SOURCE_ID.toString())
                .targetId(SECOND_TARGET_ID.toString())
                .rideId(SECOND_RIDE_ID)
                .rating(SECOND_RATING)
                .comment(SECOND_COMMENT)
                .build();
    }

    public RatingResponse getRatingResponse() {
        return RatingResponse.builder()
                .id(ID)
                .sourceId(SOURCE_ID)
                .targetId(TARGET_ID)
                .rideId(RIDE_ID)
                .rating(RATING)
                .comment(COMMENT)
                .creationTime(CREATION_TIME)
                .build();
    }

    public RatingResponse getNonExistingRatingResponse() {
        return RatingResponse.builder()
                .id(ID)
                .sourceId(TARGET_ID)
                .targetId(SOURCE_ID)
                .rideId(RIDE_ID)
                .rating(RATING)
                .comment(COMMENT)
                .creationTime(CREATION_TIME)
                .build();
    }

    public RatingResponse getSecondRatingResponse() {
        return RatingResponse.builder()
                .id(SECOND_ID)
                .sourceId(SECOND_SOURCE_ID)
                .targetId(SECOND_TARGET_ID)
                .rideId(SECOND_RIDE_ID)
                .rating(SECOND_RATING)
                .comment(SECOND_COMMENT)
                .creationTime(SECOND_CREATION_TIME)
                .build();
    }

    public RatingResponse getEditRatingResponse() {
        return RatingResponse.builder()
                .id(ID)
                .sourceId(SECOND_SOURCE_ID)
                .targetId(SECOND_TARGET_ID)
                .rideId(SECOND_RIDE_ID)
                .rating(SECOND_RATING)
                .comment(SECOND_COMMENT)
                .creationTime(CREATION_TIME)
                .build();
    }

    public List<RatingResponse> getRatingResponses() {
        return List.of(
                getRatingResponse(),
                getSecondRatingResponse()
        );
    }

    public List<Feedback> getOneFeedbacks() {
        return List.of(
                Feedback.builder()
                        .rating(RATING)
                        .comment(COMMENT)
                        .creationTime(CREATION_TIME)
                        .build()
        );
    }

    public List<Feedback> getFeedbacks() {
        return List.of(
                Feedback.builder()
                        .rating(RATING)
                        .comment(COMMENT)
                        .creationTime(CREATION_TIME)
                        .build(),
                Feedback.builder()
                        .rating(SECOND_RATING)
                        .comment(SECOND_COMMENT)
                        .creationTime(SECOND_CREATION_TIME)
                        .build()
        );
    }

    public RatingCreateEvent getRatingCreateEvent() {
        return RatingCreateEvent.builder()
                .rideId(RIDE_ID)
                .sourceId(SOURCE_ID)
                .targetId(TARGET_ID)
                .rating(RATING)
                .comment(COMMENT)
                .build();
    }

    public void updateRating(Rating rating, RatingRequest ratingRequest) {
        rating.setSourceId(UUID.fromString(ratingRequest.getSourceId()));
        rating.setTargetId(UUID.fromString(ratingRequest.getTargetId()));
        rating.setRideId(ratingRequest.getRideId());
        rating.setRating(ratingRequest.getRating());
        rating.setComment(ratingRequest.getComment());
    }

    public FindAllParams getFindAllParams() {
        return FindAllParams.builder()
                .limit(LIMIT)
                .sort(SORT)
                .build();
    }

    public Map<String, Object> getFindAllParamsMap() {
        return Map.of(
                "page", PAGE,
                "limit", LIMIT,
                "sort", SORT_NAME
        );
    }


    public Query getQuery() {
        return Query.query(new ArrayList<>())
                .pageRequest(getPageRequest())
                .withAllowFiltering();
    }

    public PageRequest getPageRequest() {
        return PageRequest.ofSize(LIMIT);
    }

    public Slice<Rating> getSliceOfRatings() {
        return new SliceImpl<>(getRatings());
    }

    public PageResponse<RatingResponse> getPageResponse() {
        return PageResponse.<RatingResponse>builder()
                .objectList(getRatingResponses())
                .build();
    }

    public PageResponse<RatingResponse> getPageResponse(List<RatingResponse> ratingResponses) {
        return PageResponse.<RatingResponse>builder()
                .objectList(ratingResponses)
                .build();
    }
}
