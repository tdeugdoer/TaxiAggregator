package com.tserashkevich.rideservice.services;

import com.tserashkevich.rideservice.dtos.*;
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
    RideResponse changeStatus(String rideId, StatusRequest statusRequest);
    RideResponse changeDriver(String rideId, DriverIdRequest driverIdRequest);
    RideResponse changeCar(String rideId, CarIdRequest carIdRequest);
}
