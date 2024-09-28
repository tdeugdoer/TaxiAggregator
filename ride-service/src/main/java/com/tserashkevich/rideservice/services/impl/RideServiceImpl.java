package com.tserashkevich.rideservice.services.impl;

import com.tserashkevich.rideservice.dtos.*;
import com.tserashkevich.rideservice.exceptions.RideNotFoundException;
import com.tserashkevich.rideservice.kafka.CreateRatingProducer;
import com.tserashkevich.rideservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.rideservice.mappers.RideMapper;
import com.tserashkevich.rideservice.models.Ride;
import com.tserashkevich.rideservice.models.enums.Status;
import com.tserashkevich.rideservice.repositories.RideRepository;
import com.tserashkevich.rideservice.services.RideService;
import com.tserashkevich.rideservice.utils.LogList;
import com.tserashkevich.rideservice.utils.QueryPredicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final MongoTemplate mongoTemplate;
    private final CreateRatingProducer createRatingProducer;

    @Override
    public CreateRideResponse create(CreateRideRequest createRideRequest) {
        Ride ride = rideMapper.toModel(createRideRequest);
        rideRepository.save(ride);
        log.info(LogList.CREATE_RIDE, ride.getId());
        return rideMapper.toCreateRideResponse(ride);
    }

    @Override
    public void delete(String rideId) {
        rideRepository.delete(getOrThrow(rideId));
        log.info(LogList.DELETE_RIDE, rideId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<RideResponse> findAll(FindAllParams findAllParams) {
        Pageable pageable = PageRequest.of(findAllParams.getPage(), findAllParams.getLimit(), findAllParams.getSort());
        Query query = QueryPredicate.builder()
                .add(findAllParams.getCarId(), Criteria.where("driverId").is(findAllParams.getDriverId()))
                .add(findAllParams.getPassengerId(), Criteria.where("passengerId").is(findAllParams.getPassengerId()))
                .add(findAllParams.getStartTime(), Criteria.where("time.startTime").gte(findAllParams.getStartTime()))
                .add(findAllParams.getEndTime(), Criteria.where("time.endTime").lte(findAllParams.getEndTime()))
                .add(findAllParams.getMinDistance(), Criteria.where("distance").gte(findAllParams.getMinDistance()))
                .add(findAllParams.getMaxDistance(), Criteria.where("distance").lte(findAllParams.getMaxDistance()))
                .add(findAllParams.getStatus(), Criteria.where("status").gte(findAllParams.getStatus()))
                .add(findAllParams.getCarId(), Criteria.where("carId").is(findAllParams.getCarId()))
                .with(pageable)
                .build();
        Page<Ride> ridePage = PageableExecutionUtils.getPage(mongoTemplate.find(query, Ride.class),
                pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1),
                        Ride.class));
        List<RideResponse> rideResponses = rideMapper.toRideResponses(ridePage.getContent());
        log.info(LogList.FIND_ALL_RIDES);
        return PageResponse.<RideResponse>builder()
                .objectList(rideResponses)
                .totalElements(ridePage.getTotalElements())
                .totalPages(ridePage.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public RideResponse findById(String rideId) {
        Ride ride = getOrThrow(rideId);
        log.info(LogList.FIND_RIDE, rideId);
        return rideMapper.toRideResponse(ride);
    }

    @Transactional
    @Override
    public RideResponse changeStatus(String rideId, String status) {
        Ride ride = getOrThrow(rideId);
        ride.setStatus(Status.valueOf(status));
        if (ride.getStatus().equals(Status.FINISHED)) {
            ride.getTime().setEndTime(LocalDateTime.now());
        }
        rideRepository.save(ride);
        log.info(LogList.CHANGE_STATUS, rideId);
        return rideMapper.toRideResponse(ride);
    }

    @Transactional
    @Override
    public RideResponse changeDriver(String rideId, String driverId) {
        Ride ride = getOrThrow(rideId);
        ride.setDriverId(UUID.fromString(driverId));
        rideRepository.save(ride);
        log.info(LogList.CHANGE_DRIVER, rideId);
        return rideMapper.toRideResponse(ride);
    }

    @Transactional
    @Override
    public RideResponse changeCar(String rideId, Long carId) {
        Ride ride = getOrThrow(rideId);
        ride.setCarId(carId);
        rideRepository.save(ride);
        log.info(LogList.CHANGE_CAR, rideId);
        return rideMapper.toRideResponse(ride);
    }

    @Override
    public void createDriverComment(CreateRatingRequest createRatingRequest) {
        Ride ride = getOrThrow(createRatingRequest.getRideId());
        RatingCreateEvent ratingCreateEvent = RatingCreateEvent.builder()
                .rideId(ride.getId())
                .sourceId(ride.getDriverId())
                .targetId(ride.getPassengerId())
                .rating(createRatingRequest.getRating())
                .comment(createRatingRequest.getComment())
                .build();
        createRatingProducer.sendRatingCreateEvent(ratingCreateEvent);
    }

    @Override
    public void createPassengerComment(CreateRatingRequest createRatingRequest) {
        Ride ride = getOrThrow(createRatingRequest.getRideId());
        RatingCreateEvent ratingCreateEvent = RatingCreateEvent.builder()
                .rideId(ride.getId())
                .sourceId(ride.getPassengerId())
                .targetId(ride.getDriverId())
                .rating(createRatingRequest.getRating())
                .comment(createRatingRequest.getComment())
                .build();
        createRatingProducer.sendRatingCreateEvent(ratingCreateEvent);
    }

    public Ride getOrThrow(String rideId) {
        Optional<Ride> optionalRide = rideRepository.findById(rideId);
        return optionalRide.orElseThrow(RideNotFoundException::new);
    }
}
