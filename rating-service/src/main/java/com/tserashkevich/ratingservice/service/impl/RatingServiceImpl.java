package com.tserashkevich.ratingservice.service.impl;

import com.datastax.oss.driver.api.core.cql.PagingState;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.tserashkevich.ratingservice.dtos.PageResponse;
import com.tserashkevich.ratingservice.dtos.RatingRequest;
import com.tserashkevich.ratingservice.dtos.RatingResponse;
import com.tserashkevich.ratingservice.exceptions.RatingNotFoundException;
import com.tserashkevich.ratingservice.mappers.RatingMapper;
import com.tserashkevich.ratingservice.models.Rating;
import com.tserashkevich.ratingservice.repositories.RatingRepository;
import com.tserashkevich.ratingservice.service.RatingService;
import com.tserashkevich.ratingservice.utils.LogList;
import com.tserashkevich.ratingservice.utils.QueryPredicate;
import com.tserashkevich.ratingservice.utils.RatingSortList;
import com.tserashkevich.ratingservice.utils.RatingSorter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.CassandraScrollPosition;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
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
        rating.setId(UUID.randomUUID());
        rating.setCreationTime(LocalDateTime.now());
        ratingRepository.save(rating);
        log.info(LogList.CREATE_RATING, rating.getId());
        return ratingMapper.toResponse(rating);
    }

    @Override
    public RatingResponse update(UUID ratingId, RatingRequest ratingRequest) {
        Rating rating = getOrThrow(ratingId);
        ratingMapper.updateModel(rating, ratingRequest);
        ratingRepository.save(rating);
        log.info(LogList.EDIT_RATING, ratingId);
        return ratingMapper.toResponse(rating);
    }

    @Override
    public void delete(UUID ratingId) {
        ratingRepository.delete(getOrThrow(ratingId));
        log.info(LogList.DELETE_RATING, ratingId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<RatingResponse> findAll(int limit, RatingSortList sort, UUID sourceId, UUID targetId, Integer rating) {
        Pageable pageable = PageRequest.ofSize(limit);
        Query query = QueryPredicate.builder()
                .add(sourceId, Criteria.where("source_id").is(sourceId))
                .add(targetId, Criteria.where("target_id").is(targetId))
                .add(rating, Criteria.where("rating").is(rating))
                .with(pageable)
                .build();
        Slice<Rating> sliceRatings = cassandraTemplate.slice(query, Rating.class);
        List<RatingResponse> ratingResponses = ratingMapper.toResponses(RatingSorter.sortRating(sliceRatings.getContent(), sort));
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
        return ratingMapper.toResponse(rating);
    }

    public Rating getOrThrow(UUID ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        return optionalRating.orElseThrow(RatingNotFoundException::new);
    }
}
