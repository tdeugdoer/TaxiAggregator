package com.tserashkevich.rideservice.services;

import com.tserashkevich.rideservice.dtos.*;

public interface RideService {
    CreateRideResponse create(CreateRideRequest createRideRequest);

    void delete(String rideId);

    PageResponse<RideResponse> findAll(FindAllParams findAllParams);

    RideResponse findById(String rideId);

    RideResponse changeStatus(String rideId, String status);

    RideResponse changeDriver(String rideId, String driverId);

    RideResponse changeCar(String rideId, Long carId);

    void createDriverComment(CreateRatingRequest createRatingRequest);

    void createPassengerComment(CreateRatingRequest createRatingRequest);
}
