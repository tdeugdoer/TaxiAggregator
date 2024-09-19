package com.tserashkevich.rideservice.services.impl;

import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.dtos.CreateRideResponse;
import com.tserashkevich.rideservice.dtos.PageResponse;
import com.tserashkevich.rideservice.dtos.RideResponse;
import com.tserashkevich.rideservice.exceptions.RideNotFoundException;
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
import org.springframework.data.domain.Sort;
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
    public PageResponse<RideResponse> findAll(int page, int limit, Sort sort,
                                              UUID driverId, UUID passengerId,
                                              LocalDateTime startTime, LocalDateTime endTime,
                                              Integer minDistance, Integer maxDistance,
                                              Status status, Long carId) {
        Pageable pageable = PageRequest.of(page, limit, sort);
        Query query = QueryPredicate.builder()
                .add(driverId, Criteria.where("driverId").is(driverId))
                .add(passengerId, Criteria.where("passengerId").is(passengerId))
                .add(startTime, Criteria.where("time.startTime").gte(startTime))
                .add(endTime, Criteria.where("time.endTime").lte(endTime))
                .add(minDistance, Criteria.where("distance").gte(minDistance))
                .add(maxDistance, Criteria.where("distance").lte(maxDistance))
                .add(status, Criteria.where("time.startTime").gte(startTime))
                .add(carId, Criteria.where("carId").is(carId))
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
        return rideMapper.toRideResponse(ride);
    }

    @Transactional
    @Override
    public RideResponse changeDriver(String rideId, String driverId) {
        Ride ride = getOrThrow(rideId);
        ride.setDriverId(UUID.fromString(driverId));
        rideRepository.save(ride);
        return rideMapper.toRideResponse(ride);
    }

    @Transactional
    @Override
    public RideResponse changeCar(String rideId, Long carId) {
        Ride ride = getOrThrow(rideId);
        ride.setCarId(carId);
        rideRepository.save(ride);
        return rideMapper.toRideResponse(ride);
    }

    public Ride getOrThrow(String rideId) {
        Optional<Ride> optionalRide = rideRepository.findById(rideId);
        return optionalRide.orElseThrow(RideNotFoundException::new);
    }
}
