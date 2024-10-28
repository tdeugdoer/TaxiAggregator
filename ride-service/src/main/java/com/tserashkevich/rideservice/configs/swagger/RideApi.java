package com.tserashkevich.rideservice.configs.swagger;

import com.tserashkevich.rideservice.dtos.*;
import com.tserashkevich.rideservice.models.enums.Status;
import com.tserashkevich.rideservice.utils.PatternList;
import com.tserashkevich.rideservice.utils.RideSortList;
import com.tserashkevich.rideservice.utils.ValidationList;
import com.tserashkevich.rideservice.validators.validAnnotations.CarExist;
import com.tserashkevich.rideservice.validators.validAnnotations.DriverExist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

public interface RideApi {
    @Operation(summary = "Create a new ride", description = "Create a new ride on the provided data.")
    @ApiResponse(responseCode = "201", description = "Ride created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    CreateRideResponse createRide(@Parameter(description = "Create ride request") @Valid @RequestBody CreateRideRequest createRideRequest);

    @Operation(summary = "Delete a ride", description = "Deleting an existing ride.")
    @ApiResponse(responseCode = "204", description = "Ride deleted successfully")
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void deleteRide(@Parameter(description = "Id of a deleting ride") @PathVariable String id);

    @Operation(summary = "Find all rides", description = "Returns a paginated list of all rides.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Wrong request parameter type",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Wrong request parameter value",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    PageResponse<RideResponse> findAllRides(@Parameter(description = "Page number") @RequestParam(defaultValue = "0") @Min(0) int page,
                                            @Parameter(description = "Items limit") @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                            @Parameter(description = "Sort value") @RequestParam(defaultValue = "ID_ASC") RideSortList sort,
                                            @Parameter(description = "Filter driverId") @RequestParam(required = false) UUID driverId,
                                            @Parameter(description = "Filter rideId") @RequestParam(required = false) UUID rideId,
                                            @Parameter(description = "Filter carId") @RequestParam(required = false) Long carId,
                                            @Parameter(description = "Filter start time") @RequestParam(required = false) LocalDateTime startTime,
                                            @Parameter(description = "Filter end time") @RequestParam(required = false) LocalDateTime endTime,
                                            @Parameter(description = "Filter min distance") @RequestParam(required = false) Integer minDistance,
                                            @Parameter(description = "Filter max distance") @RequestParam(required = false) Integer maxDistance,
                                            @Parameter(description = "Filter status") @RequestParam(required = false) Status status);

    @Operation(summary = "Find ride by ID", description = "Returns a ride by its ID.")
    @ApiResponse(responseCode = "200", description = "Ride found by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    RideResponse findRideById(@Parameter(description = "Id of а finding ride") @PathVariable String id);

    @Operation(summary = "Change ride status", description = "Returns a ride response with changed status by ride ID.")
    @ApiResponse(responseCode = "200", description = "Ride found by id and changed status",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "400", description = "Status has wrong value",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    RideResponse changeStatus(@Parameter(description = "Ride ID") @PathVariable String rideId,
                              @Parameter(description = "Status") @PathVariable Status status);

    @Operation(summary = "Change driver by ride ID", description = "Returns a ride with changed driver ID by ride ID.")
    @ApiResponse(responseCode = "200", description = "Ride found by ride id and changed driver ID",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    RideResponse changeDriver(@Parameter(description = "Ride ID") @PathVariable String rideId,
                              @Parameter(description = "Driver ID")
                              @DriverExist(message = ValidationList.DRIVER_NOT_EXIST)
                              @NotBlank(message = ValidationList.DRIVER_ID_REQUIRED)
                              @Pattern(regexp = PatternList.UUID_PATTERN, message = ValidationList.WRONG_UUID_FORMAT)
                              @PathVariable String driverId);

    @Operation(summary = "Change car by ride ID", description = "Returns a ride with changed car ID by ride ID.")
    @ApiResponse(responseCode = "200", description = "Ride found by ride id and changed car ID",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "400", description = "Car ID is not Long",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    RideResponse changeCar(@Parameter(description = "Ride ID") @PathVariable String rideId,
                           @Parameter(description = "Car ID")
                           @CarExist(message = ValidationList.CAR_NOT_EXIST)
                           @NotNull(message = ValidationList.CAR_ID_REQUIRED)
                           @Min(value = 1, message = ValidationList.NEGATIVE_VALUE)
                           @PathVariable Long carId);

    @Operation(summary = "Create driver comment by ride ID", description = "Send the message to other service about creating an assessment by the passenger's driver.")
    @ApiResponse(responseCode = "200", description = "Message sent successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ride not finished",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void createDriverComment(@Parameter(description = "Create ride request") @Valid @RequestBody CreateRatingRequest createRatingRequest);

    @Operation(summary = "Create passenger comment by ride ID", description = "Send the message to other service about creating an assessment by the driver's passenger.")
    @ApiResponse(responseCode = "200", description = "Message sent successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RideResponse.class)))
    @ApiResponse(responseCode = "400", description = "Ride not finished",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ride not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void createPassengerComment(@Parameter(description = "Create ride request") @Valid @RequestBody CreateRatingRequest createRatingRequest);
}
