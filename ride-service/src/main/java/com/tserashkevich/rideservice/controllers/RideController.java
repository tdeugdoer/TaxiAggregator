package com.tserashkevich.rideservice.controllers;

import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.dtos.CreateRideResponse;
import com.tserashkevich.rideservice.dtos.PageResponse;
import com.tserashkevich.rideservice.dtos.RideResponse;
import com.tserashkevich.rideservice.models.enums.Status;
import com.tserashkevich.rideservice.services.RideService;
import com.tserashkevich.rideservice.utils.PatternList;
import com.tserashkevich.rideservice.utils.RideSortList;
import com.tserashkevich.rideservice.utils.ValidationList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
    public RideResponse changeStatus(@PathVariable String rideId,
                                     @NotBlank(message = ValidationList.STATUS_REQUIRED)
                                     @Pattern(regexp = PatternList.STATUS_PATTERN, message = ValidationList.WRONG_STATUS)
                                     @RequestParam String status) {
        return rideService.changeStatus(rideId, status);
    }

    @PutMapping("/changeDriver/{rideId}")
    public RideResponse changeDriver(@PathVariable String rideId,
                                     @NotBlank(message = ValidationList.DRIVER_ID_REQUIRED)
                                     @Pattern(regexp = PatternList.UUID_PATTERN, message = ValidationList.WRONG_UUID_FORMAT)
                                     @RequestParam String driverId) {
        return rideService.changeDriver(rideId, driverId);
    }

    @PutMapping("/changeCar/{rideId}")
    public RideResponse changeCar(@PathVariable String rideId,
                                  @NotNull(message = ValidationList.CAR_ID_REQUIRED)
                                  @Min(value = 1, message = ValidationList.NEGATIVE_VALUE)
                                  Long carId) {
        return rideService.changeCar(rideId, carId);
    }
}
