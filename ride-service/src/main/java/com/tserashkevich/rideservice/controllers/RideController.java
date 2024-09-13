package com.tserashkevich.rideservice.controllers;

import com.tserashkevich.rideservice.dtos.*;
import com.tserashkevich.rideservice.models.enums.Status;
import com.tserashkevich.rideservice.services.RideService;
import com.tserashkevich.rideservice.utils.RideSortList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/rides")
public class RideController {
    private final RideService rideService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateRideResponse createRide(@Valid @RequestBody CreateRideRequest rideRequest) {
        return rideService.create(rideRequest);
    }

    @DeleteMapping("/{rideId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRide(@PathVariable String rideId) {
        rideService.delete(rideId);
    }

    @GetMapping
    public PageResponse<RideResponse> findAllRides(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                   @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                   @RequestParam(defaultValue = "ID_ASC") RideSortList sort,
                                                   @RequestParam(required = false) UUID driverId,
                                                   @RequestParam(required = false) UUID passengerId,
                                                   @RequestParam(required = false) Long carId,
                                                   @RequestParam(required = false) LocalDateTime startTime,
                                                   @RequestParam(required = false) LocalDateTime endTime,
                                                   @RequestParam(required = false) Integer minDistance,
                                                   @RequestParam(required = false) Integer maxDistance,
                                                   @RequestParam(required = false) Status status) {
        return rideService.findAll(page, limit, sort.getValue(), driverId, passengerId, startTime, endTime, minDistance, maxDistance, status, carId);
    }

    @GetMapping("/{rideId}")
    public RideResponse findRideById(@PathVariable String rideId) {
        return rideService.findById(rideId);
    }

    @PutMapping("/changeStatus/{rideId}")
    public RideResponse changeStatus(@PathVariable String rideId, @RequestBody StatusRequest statusRequest) {
        return rideService.changeStatus(rideId, statusRequest);
    }

    @PutMapping("/changeDriver/{rideId}")
    public RideResponse changeDriver(@PathVariable String rideId, @RequestBody DriverIdRequest driverIdRequest) {
        return rideService.changeDriver(rideId, driverIdRequest);
    }

    @PutMapping("/changeCar/{rideId}")
    public RideResponse changeCar(@PathVariable String rideId, @RequestBody CarIdRequest carIdRequest) {
        return rideService.changeCar(rideId, carIdRequest);
    }
}
