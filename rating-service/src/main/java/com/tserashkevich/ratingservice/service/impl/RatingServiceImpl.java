package com.tserashkevich.ratingservice.service.impl;

import com.tserashkevich.ratingservice.dtos.*;
import com.tserashkevich.ratingservice.exceptions.RatingExistException;
import com.tserashkevich.ratingservice.exceptions.RatingNotFoundException;
import com.tserashkevich.ratingservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.ratingservice.mappers.RatingMapper;
import com.tserashkevich.ratingservice.models.Rating;
import com.tserashkevich.ratingservice.repositories.RatingRepository;
import com.tserashkevich.ratingservice.service.RatingService;
import com.tserashkevich.ratingservice.utils.LogList;
import com.tserashkevich.ratingservice.utils.QueryPredicate;
import com.tserashkevich.ratingservice.utils.RatingSorter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final CassandraTemplate cassandraTemplate;

    @Override
    public RatingResponse create(RatingRequest ratingRequest) {
        Rating rating = ratingMapper.toModel(ratingRequest);
        checkRatingExist(rating.getRideId(), rating.getSourceId());

        rating.setId(UUID.randomUUID());
        rating.setCreationTime(LocalDateTime.now());

        ratingRepository.save(rating);
        log.info(LogList.CREATE_RATING, rating.getId());
        return ratingMapper.toRatingResponse(rating);
    }

    @Override
    public void create(RatingCreateEvent ratingCreateEvent) {
        Rating rating = ratingMapper.toModel(ratingCreateEvent);
        if (ratingRepository.existByRideIdAndSourceId(rating.getRideId(), rating.getSourceId()) == 0){
            rating.setId(UUID.randomUUID());
            rating.setCreationTime(LocalDateTime.now());
            ratingRepository.save(rating);
            log.info(LogList.CREATE_RATING, rating.getId());
        }
        else log.info(LogList.RATING_EXIST);
    }

    @Override
    public RatingResponse update(UUID ratingId, RatingRequest ratingRequest) {
        Rating rating = getOrThrow(ratingId);
        ratingMapper.updateModel(rating, ratingRequest);
        ratingRepository.save(rating);
        log.info(LogList.EDIT_RATING, ratingId);
        return ratingMapper.toRatingResponse(rating);
    }

    @Override
    public void delete(UUID ratingId) {
        ratingRepository.delete(getOrThrow(ratingId));
        log.info(LogList.DELETE_RATING, ratingId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<RatingResponse> findAll(FindAllParams findAllParams) {
        Pageable pageable = PageRequest.ofSize(findAllParams.getLimit());
        Query query = QueryPredicate.builder()
                .add(findAllParams.getSourceId(), Criteria.where("source_id").is(findAllParams.getSourceId()))
                .add(findAllParams.getTargetId(), Criteria.where("target_id").is(findAllParams.getTargetId()))
                .add(findAllParams.getRating(), Criteria.where("rating").is(findAllParams.getRating()))
                .with(pageable)
                .build();
        Slice<Rating> sliceRatings = cassandraTemplate.slice(query, Rating.class);
        List<RatingResponse> ratingResponses = ratingMapper.toRatingResponses(RatingSorter.sortRating(sliceRatings.getContent(), findAllParams.getSort()));
        log.info(LogList.FIND_ALL_RATINGS);
        return PageResponse.<RatingResponse>builder()
                .objectList(ratingResponses)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public RatingResponse findById(UUID ratingId) {
        Rating rating = getOrThrow(ratingId);
        log.info(LogList.FIND_RATING, ratingId);
        return ratingMapper.toRatingResponse(rating);
    }

    @Override
    public Double findAvgRating(UUID targetId) {
        Double avgRating = ratingRepository.countTargetIdAvgRating(targetId);
        log.info(LogList.COUNT_AVG_RATING, targetId);
        return avgRating;
    }

    @Override
    public List<Feedback> findFeedbacks(UUID targetId) {
        List<Rating> ratings = ratingRepository.findAllByTargetId(targetId);
        log.info(LogList.FIND_FEEDBACKS, targetId);
        return ratingMapper.toFeedbacks(ratings);
    }

    private Rating getOrThrow(UUID ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        return optionalRating.orElseThrow(RatingNotFoundException::new);
    }

    public void checkRatingExist(String rideId, UUID sourceId) {
        if (ratingRepository.existByRideIdAndSourceId(rideId, sourceId) != 0)
            throw new RatingExistException();
    }
}
