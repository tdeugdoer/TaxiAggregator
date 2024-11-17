package com.tserashkevich.driverservice.configs.swagger;

import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.models.enums.Gender;
import com.tserashkevich.driverservice.utils.DriverSortList;
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

public interface DriverApi {
    @Operation(summary = "Create a new driver", description = "Create a new driver on the provided data.")
    @ApiResponse(responseCode = "201", description = "Driver created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DriverResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    DriverResponse createDriver(@Parameter(description = "Driver request") @Valid @RequestBody DriverRequest driverRequest);

    @Operation(summary = "Update a driver", description = "Update an existing driver on the provided data.")
    @ApiResponse(responseCode = "200", description = "Driver updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DriverResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Id is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    DriverResponse updateDriver(@Parameter(description = "Id of a updating driver") @PathVariable UUID driverId,
                                @Parameter(description = "Driver update request") @Valid @RequestBody DriverUpdateRequest driverUpdateRequest);

    @Operation(summary = "Delete a driver", description = "Deleting an existing driver.")
    @ApiResponse(responseCode = "204", description = "Driver deleted successfully")
    @ApiResponse(responseCode = "400", description = "Id is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void deleteDriver(@Parameter(description = "Id of a deleting driver") @PathVariable UUID id);

    @Operation(summary = "Find all drivers", description = "Returns a paginated list of all drivers.")
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
    PageResponse<DriverResponse> findAllDrivers(@Parameter(description = "Page number") @RequestParam(defaultValue = "0") @Min(0) int page,
                                                @Parameter(description = "Items limit") @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                @Parameter(description = "Sort value") @RequestParam(defaultValue = "ID_ASC") DriverSortList sort,
                                                @Parameter(description = "Filter gender") @RequestParam(required = false) Gender gender,
                                                @Parameter(description = "Filter start birthdate") @RequestParam(required = false) LocalDate birthDateStart,
                                                @Parameter(description = "Filter end birthdate") @RequestParam(required = false) LocalDate birthDateEnd,
                                                @Parameter(description = "Filter available status") @RequestParam(required = false) Boolean available);

    @Operation(summary = "Find driver by ID", description = "Returns a driver by its ID.")
    @ApiResponse(responseCode = "200", description = "Driver found by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DriverResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    DriverResponse findDriverById(@Parameter(description = "Id of а finding driver") @PathVariable UUID id);

    @Operation(summary = "Change driver status by ID", description = "Returns a driver by its ID with changed status.")
    @ApiResponse(responseCode = "200", description = "Driver found by id and changed status",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DriverResponse.class)))
    @ApiResponse(responseCode = "400", description = "Wrong request parameter type",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Driver not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    DriverResponse changeAvailableStatus(@Parameter(description = "Id of a changing status driver") @PathVariable UUID driverId,
                                         @Parameter(description = "New driver status") @PathVariable Boolean available);

    @Operation(summary = "Exist driver by ID", description = "Returns true if a driver by its ID exist and false if not exist.")
    @ApiResponse(responseCode = "200", description = "Boolean value of existence",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DriverResponse.class)))
    @ApiResponse(responseCode = "400", description = "Driver ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    Boolean existDriver(@Parameter(description = "Id of а checking driver") @PathVariable UUID driverId);
}
