package com.tserashkevich.rideservice.services;

import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.dtos.CreateRideResponse;
import com.tserashkevich.rideservice.dtos.PageResponse;
import com.tserashkevich.rideservice.dtos.RideResponse;
import com.tserashkevich.rideservice.models.enums.Status;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RideService {
    CreateRideResponse create(CreateRideRequest createRideRequest);
    void delete(String rideId);
    PageResponse<RideResponse> findAll(int page, int limit, Sort sort,
                                       UUID driverId, UUID passengerId,
                                       LocalDateTime startTime, LocalDateTime endTime,
                                       Integer minDistance, Integer maxDistance,
                                       Status status, Long carId);
    RideResponse findById(String rideId);
    RideResponse changeStatus(String rideId, String status);
    RideResponse changeDriver(String rideId, String driverId);
    RideResponse changeCar(String rideId, Long carId);
}
