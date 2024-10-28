package com.tserashkevich.passengerservice.configs.swagger;

import com.tserashkevich.passengerservice.dtos.*;
import com.tserashkevich.passengerservice.models.enums.Gender;
import com.tserashkevich.passengerservice.utils.SortList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

public interface PassengerApi {
    @Operation(summary = "Create a new passenger", description = "Create a new passenger on the provided data.")
    @ApiResponse(responseCode = "201", description = "Passenger created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    PassengerResponse createPassenger(@Parameter(description = "Passenger request") @Valid @RequestBody PassengerRequest passengerRequest);

    @Operation(summary = "Update a passenger", description = "Update an existing passenger on the provided data.")
    @ApiResponse(responseCode = "200", description = "Passenger updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Id is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    PassengerResponse updatePassenger(@Parameter(description = "Id of a updating passenger") @PathVariable UUID id,
                                      @Parameter(description = "Passenger request") @Valid @RequestBody PassengerRequest passengerRequest);

    @Operation(summary = "Delete a passenger", description = "Deleting an existing passenger.")
    @ApiResponse(responseCode = "204", description = "Passenger deleted successfully")
    @ApiResponse(responseCode = "400", description = "Id is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Passenger not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void deletePassenger(@Parameter(description = "Id of a deleting passenger") @PathVariable UUID id);

    @Operation(summary = "Find all passengers", description = "Returns a paginated list of all passengers.")
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
    PageResponse<PassengerResponse> findAllPassengers(@Parameter(description = "Page number") @RequestParam(defaultValue = "0") @Min(0) int page,
                                                      @Parameter(description = "Items limit") @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                      @Parameter(description = "Sort value") @RequestParam(defaultValue = "ID_ASC") SortList sort,
                                                      @Parameter(description = "Filter gender") @RequestParam(required = false) Gender gender,
                                                      @Parameter(description = "Filter start birthdate") @RequestParam(required = false) LocalDate birthDateStart,
                                                      @Parameter(description = "Filter end birthdate") @RequestParam(required = false) LocalDate birthDateEnd);

    @Operation(summary = "Find passenger by ID", description = "Returns a passenger by its ID.")
    @ApiResponse(responseCode = "200", description = "Passenger found by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Passenger not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    PassengerResponse findPassengerById(@Parameter(description = "Id of а finding passenger") @PathVariable UUID id);

    @Operation(summary = "Exist passenger by ID", description = "Returns true if a passenger by its ID exist and false if not exist.")
    @ApiResponse(responseCode = "200", description = "Boolean value of existence",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PassengerResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    Boolean existPassenger(@Parameter(description = "Id of а checking passenger") @PathVariable UUID passengerId);
}
