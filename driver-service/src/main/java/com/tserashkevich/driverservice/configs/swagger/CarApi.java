package com.tserashkevich.driverservice.configs.swagger;

import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.models.enums.Color;
import com.tserashkevich.driverservice.utils.CarSortList;
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

public interface CarApi {
    @Operation(summary = "Create a new car", description = "Create a new car on the provided data.")
    @ApiResponse(responseCode = "201", description = "Car created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    CarResponse createCar(@Parameter(description = "Car request") @Valid @RequestBody CarRequest carRequest);

    @Operation(summary = "Update a car", description = "Update an existing car on the provided data.")
    @ApiResponse(responseCode = "200", description = "Car updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Id is not Long",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    CarResponse updateCar(@Parameter(description = "Id of a updating car") @PathVariable Long carId,
                          @Parameter(description = "Car request") @Valid @RequestBody CarRequest carRequest);

    @Operation(summary = "Delete a car", description = "Deleting an existing car.")
    @ApiResponse(responseCode = "204", description = "Car deleted successfully")
    @ApiResponse(responseCode = "400", description = "Id is not Long",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void deleteCar(@Parameter(description = "Id of a deleting car") @PathVariable Long id);

    @Operation(summary = "Find all cars", description = "Returns a paginated list of all cars.")
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
    PageResponse<CarResponse> findAllCars(@Parameter(description = "Page number") @RequestParam(defaultValue = "0") @Min(0) int page,
                                          @Parameter(description = "Items limit") @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                          @Parameter(description = "Sort value") @RequestParam(defaultValue = "ID_ASC") CarSortList sort,
                                          @Parameter(description = "Filter number") @RequestParam(required = false) String number,
                                          @Parameter(description = "Filter brand") @RequestParam(required = false) String brand,
                                          @Parameter(description = "Filter model") @RequestParam(required = false) String model,
                                          @Parameter(description = "Filter color") @RequestParam(required = false) Color color);

    @Operation(summary = "Find car by ID", description = "Returns a car by its ID.")
    @ApiResponse(responseCode = "200", description = "Car found by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID is not Long",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Car not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "External service not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "External service: connection exception, server exception, service isn't responding",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    CarResponse findCarById(@Parameter(description = "Id of а finding car") @PathVariable Long id);

    @Operation(summary = "Exist car by ID", description = "Returns true if a car by its ID exist and false if not exist.")
    @ApiResponse(responseCode = "200", description = "Boolean value of existence",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CarResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID is not Long",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    Boolean existCar(@Parameter(description = "Id of а checking car") @PathVariable Long carId);
}
