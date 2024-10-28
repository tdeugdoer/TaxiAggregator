package com.tserashkevich.ratingservice.config.swagger;

import com.tserashkevich.ratingservice.dtos.*;
import com.tserashkevich.ratingservice.utils.RatingSortList;
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

import java.util.List;
import java.util.UUID;

public interface RatingApi {
    @Operation(summary = "Create a new rating", description = "Create a new rating on the provided data.")
    @ApiResponse(responseCode = "201", description = "Rating created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    RatingResponse createRating(@Parameter(description = "Rating request") @Valid @RequestBody RatingRequest ratingRequest);

    @Operation(summary = "Update a rating", description = "Update an existing rating on the provided data.")
    @ApiResponse(responseCode = "200", description = "Rating updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Id is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    RatingResponse updateRating(@Parameter(description = "Id of a updating rating") @PathVariable UUID id,
                                @Parameter(description = "Rating request") @Valid @RequestBody RatingRequest ratingRequest);

    @Operation(summary = "Delete a rating", description = "Deleting an existing rating.")
    @ApiResponse(responseCode = "204", description = "Rating deleted successfully")
    @ApiResponse(responseCode = "400", description = "Id is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Rating not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    void deleteRating(@Parameter(description = "Id of a deleting rating") @PathVariable UUID id);

    @Operation(summary = "Find all ratings", description = "Returns a paginated list of all ratings.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class)))
    @ApiResponse(responseCode = "400", description = "Wrong request parameter type",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Wrong request parameter value",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    PageResponse<RatingResponse> findAllRatings(@Parameter(description = "Items limit") @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                @Parameter(description = "Sort value") @RequestParam(defaultValue = "ID_ASC") RatingSortList sort,
                                                @Parameter(description = "Filter sourceId") @RequestParam(required = false) UUID sourceId,
                                                @Parameter(description = "Filter targetId") @RequestParam(required = false) UUID targetId,
                                                @Parameter(description = "Filter rideId") @RequestParam(required = false) String rideId,
                                                @Parameter(description = "Filter rating") @RequestParam(required = false) Integer rating);

    @Operation(summary = "Find rating by ID", description = "Returns a rating by its ID.")
    @ApiResponse(responseCode = "200", description = "Rating found by id",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Rating not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    RatingResponse findRatingById(@Parameter(description = "Id of а finding rating") @PathVariable UUID id);

    @Operation(summary = "Get avg rating by target ID", description = "Returns an average rating by target ID.")
    @ApiResponse(responseCode = "200", description = "Rating counted successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponse.class)))
    @ApiResponse(responseCode = "400", description = "Target ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Rating not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    Double findTargetAvgRating(@Parameter(description = "Target ID") @PathVariable UUID targetId);

    @Operation(summary = "Find feedbacks by target ID", description = "Returns the list of feedbacks by target ID.")
    @ApiResponse(responseCode = "200", description = "Feedbacks found successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingResponse.class)))
    @ApiResponse(responseCode = "400", description = "Target ID is not UUID",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Rating not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    List<Feedback> findFeedbacks(@Parameter(description = "Target ID") @PathVariable UUID targetId);
}
