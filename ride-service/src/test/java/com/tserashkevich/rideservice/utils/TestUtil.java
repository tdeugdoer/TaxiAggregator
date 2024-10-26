package com.tserashkevich.rideservice.utils;

import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.tserashkevich.rideservice.dtos.*;
import com.tserashkevich.rideservice.feing.feignDtos.GeocodeReverseResponse;
import com.tserashkevich.rideservice.feing.feignDtos.RoutingResponse;
import com.tserashkevich.rideservice.kafka.kafkaDtos.ChangeDriverStatusEvent;
import com.tserashkevich.rideservice.kafka.kafkaDtos.RatingCreateEvent;
import com.tserashkevich.rideservice.models.Address;
import com.tserashkevich.rideservice.models.Ride;
import com.tserashkevich.rideservice.models.Time;
import com.tserashkevich.rideservice.models.enums.Status;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.matching;

@UtilityClass
public class TestUtil {
    public final String RIDE_ID = "6592008029c8c3e4dc76256c";
    public final String SECOND_RIDE_ID = "507f1f77bcf86cd799439011";
    public final String NON_EXISTING_RIDE_ID = "123f1f77bcf86cd799439999";
    public final UUID PASSENGER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_PASSENGER_ID = UUID.fromString("22222222-1111-1111-1111-222222222222");
    public final UUID DRIVER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public final UUID SECOND_DRIVER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public final Long CAR_ID = 1L;
    public final Long SECOND_CAR_ID = 2L;
    public final String START_LAT = "52.093865";
    public final String START_LON = "23.706679";
    public final String SECOND_START_LAT = "54.506823";
    public final String SECOND_START_LON = "27.100678";
    public final String START_GEO_POINT_NAME = "Belarus, Brestskaya Voblasts', 224000 Brest, вуліца Гогаля, 83";
    public final String SECOND_START_GEO_POINT_NAME = "Belarus, Minsk Region, 222440 Крывасельскі сельскі Савет, Н22121";
    public final String END_LAT = "52.099302";
    public final String END_LON = "23.699555";
    public final String SECOND_END_LAT = "54.027093";
    public final String SECOND_END_LON = "27.273479";
    public final String END_GEO_POINT_NAME = "Чыгуначны каледж, Belarus, Brestskaya Voblasts', 224000 Brest";
    public final String SECOND_END_GEO_POINT_NAME = "Belarus, Minsk, 223036, Ул. Молодечненская, 7";
    public final LocalDateTime START_TIME = LocalDateTime.of(2024, 9, 29, 5, 23, 12);
    public final LocalDateTime END_TIME = LocalDateTime.of(2024, 9, 29, 5, 50, 47);
    public final LocalDateTime SECOND_START_TIME = LocalDateTime.of(2024, 10, 1, 14, 40, 54);
    public final LocalDateTime SECOND_END_TIME = LocalDateTime.now();
    public final Status STATUS_CREATED = Status.CREATED;
    public final Status STATUS_FINISHED = Status.FINISHED;
    public final Integer DISTANCE = 100;
    public final Integer SECOND_DISTANCE = 50;
    public final String COMMENT = "Комментарий";
    public final Integer RATING = 5;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final Sort SORT = Sort.by(Sort.Direction.DESC, "id");
    public final String SORT_NAME = "ID_DESC";
    public final String RIDE_NOT_FOUND_MESSAGE = "Поездка не найдена";
    public final String DEFAULT_PATH = "/api/v1/rides";
    public final String GEO_POINT_REQUEST = "/geocode/reverse";
    public final String DISTANCE_REQUEST = "/routing";
    public final String EXIST_REQUEST = "/exist/";

    public Ride getRide() {
        return Ride.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .startAddress(Address.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(Address.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(DISTANCE)
                .time(Time.builder()
                        .startTime(START_TIME)
                        .build())
                .status(Status.CREATED)
                .build();
    }

    public Ride getSecondRide() {
        return Ride.builder()
                .id(SECOND_RIDE_ID)
                .passengerId(SECOND_PASSENGER_ID)
                .driverId(SECOND_DRIVER_ID)
                .carId(SECOND_CAR_ID)
                .startAddress(Address.builder()
                        .name(SECOND_START_GEO_POINT_NAME)
                        .geoPoint(SECOND_START_LAT + "|" + SECOND_START_LON)
                        .build())
                .endAddress(Address.builder()
                        .name(SECOND_END_GEO_POINT_NAME)
                        .geoPoint(SECOND_END_LAT + "|" + SECOND_END_LON)
                        .build())
                .distance(SECOND_DISTANCE)
                .time(Time.builder()
                        .startTime(SECOND_START_TIME)
                        .endTime(SECOND_END_TIME)
                        .build())
                .status(STATUS_FINISHED)
                .build();
    }

    public Ride getNonSavedRide() {
        return Ride.builder()
                .passengerId(PASSENGER_ID)
                .startAddress(Address.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(Address.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(100)
                .time(Time.builder()
                        .startTime(START_TIME)
                        .build())
                .build();
    }

    public List<Ride> getRides() {
        return List.of(
                getRide(),
                getSecondRide()
        );
    }

    public CreateRideRequest getCreateRideRequest() {
        return CreateRideRequest.builder()
                .passengerId(PASSENGER_ID.toString())
                .startGeoPoint(START_LAT + "," + START_LON)
                .endGeoPoint(END_LAT + "," + END_LON)
                .build();
    }

    public CreateRatingRequest getNotFinishedRideCreateRatingRequest() {
        return CreateRatingRequest.builder()
                .rideId(RIDE_ID)
                .comment(COMMENT)
                .rating(RATING)
                .build();
    }

    public CreateRatingRequest getFinishedRideCreateRatingRequest() {
        return CreateRatingRequest.builder()
                .rideId(RIDE_ID)
                .comment(COMMENT)
                .rating(RATING)
                .build();
    }

    public CreateRatingRequest getNonExistingRideCreateRatingRequest() {
        return CreateRatingRequest.builder()
                .rideId(NON_EXISTING_RIDE_ID)
                .comment(COMMENT)
                .rating(RATING)
                .build();
    }

    public CreateRatingRequest getSecondFinishedRideCreateRatingRequest() {
        return CreateRatingRequest.builder()
                .rideId(SECOND_RIDE_ID)
                .comment(COMMENT)
                .rating(RATING)
                .build();
    }

    public RideResponse getRideResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .build())
                .status(STATUS_CREATED.name())
                .build();
    }

    public RideResponse getRideWithDriverResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .build())
                .status(STATUS_CREATED.name())
                .build();
    }

    public RideResponse getRideWithCarResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .carId(CAR_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .build())
                .status(STATUS_CREATED.name())
                .build();
    }

    public RideResponse getRideWithFinishedStatusResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .endTime(END_TIME)
                        .build())
                .status(STATUS_FINISHED.name())
                .build();
    }

    public RideResponse getSecondRideResponse() {
        return RideResponse.builder()
                .id(SECOND_RIDE_ID)
                .driverId(SECOND_DRIVER_ID)
                .passengerId(SECOND_PASSENGER_ID)
                .carId(SECOND_CAR_ID)
                .startAddress(AddressResponse.builder()
                        .name(SECOND_START_GEO_POINT_NAME)
                        .geoPoint(SECOND_START_LAT + "|" + SECOND_START_LON)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(SECOND_END_GEO_POINT_NAME)
                        .geoPoint(SECOND_END_LAT + "|" + SECOND_END_LON)
                        .build())
                .distance(SECOND_DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(SECOND_START_TIME)
                        .endTime(LocalDateTime.now())
                        .build())
                .status(STATUS_FINISHED.name())
                .build();
    }

    public CreateRideResponse getCreateRideResponse() {
        return CreateRideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_LAT + "|" + START_LON)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_LAT + "|" + END_LON)
                        .build())
                .distance(DISTANCE)
                .startTime(START_TIME)
                .status(STATUS_CREATED.name())
                .build();
    }

    public List<RideResponse> getRideResponses() {
        return List.of(
                getRideResponse(),
                getSecondRideResponse()
        );
    }

    public ChangeDriverStatusEvent getChangeDriverStatusEvent() {
        return ChangeDriverStatusEvent.builder()
                .driverId(DRIVER_ID)
                .available(false)
                .build();
    }

    public RatingCreateEvent getDriverRatingCreateEvent(CreateRatingRequest createRatingRequest) {
        return RatingCreateEvent.builder()
                .rideId(SECOND_RIDE_ID)
                .sourceId(SECOND_DRIVER_ID)
                .targetId(SECOND_PASSENGER_ID)
                .rating(createRatingRequest.getRating())
                .comment(createRatingRequest.getComment())
                .build();
    }

    public RatingCreateEvent getPassengerRatingCreateEvent(CreateRatingRequest createRatingRequest) {
        return RatingCreateEvent.builder()
                .rideId(SECOND_RIDE_ID)
                .sourceId(SECOND_PASSENGER_ID)
                .targetId(SECOND_DRIVER_ID)
                .rating(createRatingRequest.getRating())
                .comment(createRatingRequest.getComment())
                .build();
    }

    public FindAllParams getFindAllParams() {
        return FindAllParams.builder()
                .page(PAGE)
                .limit(LIMIT)
                .sort(SORT)
                .build();
    }

    public Map<String, Object> getFindAllParamsMap() {
        return Map.of(
                "page", PAGE,
                "limit", LIMIT,
                "sort", SORT_NAME
        );
    }

    public Query getFindQueryPredicate() {
        return new Query().with(PageRequest.of(PAGE, LIMIT, SORT));
    }

    public Page<Ride> getPageOfRides() {
        return new PageImpl<>(getRides());
    }

    public PageResponse<RideResponse> getPageResponse() {
        Page<Ride> ridePage = getPageOfRides();
        return PageResponse.<RideResponse>builder()
                .objectList(getRideResponses())
                .totalElements(ridePage.getTotalElements())
                .totalPages(ridePage.getTotalPages())
                .build();
    }

    public PageResponse<RideResponse> getPageResponse(List<RideResponse> rideResponses) {
        Page<Ride> ridePage = getPageOfRides();
        return PageResponse.<RideResponse>builder()
                .objectList(rideResponses)
                .totalElements(ridePage.getTotalElements())
                .totalPages(ridePage.getTotalPages())
                .build();
    }

    public Map<String, StringValuePattern> getGeoapifyStartGeoPointParams() {
        return Map.of(
                "lat", matching(START_LAT),
                "lon", matching(START_LON),
                "format", matching("json"),
                "apiKey", matching(".*")
        );
    }

    public Map<String, StringValuePattern> getGeoapifyEndGeoPointParams() {
        return Map.of(
                "lat", matching(END_LAT),
                "lon", matching(END_LON),
                "format", matching("json"),
                "apiKey", matching(".*")
        );
    }

    public GeocodeReverseResponse getStartGeocodeReverseResponse() {
        return GeocodeReverseResponse.builder()
                .results(List.of(
                        GeocodeReverseResponse.Result.builder()
                                .lat(Double.parseDouble(START_LAT))
                                .lon(Double.parseDouble(START_LON))
                                .formatted(START_GEO_POINT_NAME)
                                .build()
                ))
                .build();
    }

    public GeocodeReverseResponse getEndGeocodeReverseResponse() {
        return GeocodeReverseResponse.builder()
                .results(List.of(
                        GeocodeReverseResponse.Result.builder()
                                .lat(Double.parseDouble(END_LAT))
                                .lon(Double.parseDouble(END_LON))
                                .formatted(END_GEO_POINT_NAME)
                                .build()
                ))
                .build();
    }

    public Map<String, StringValuePattern> getRoutingResponseParams() {
        return Map.of(
                "waypoints", matching(".*"),
                "mode", matching("drive"),
                "apiKey", matching(".*")
        );
    }

    public RoutingResponse getRoutingResponse() {
        return RoutingResponse.builder()
                .features(List.of(
                        RoutingResponse.Feature.builder()
                                .properties(RoutingResponse.Feature.Properties.builder()
                                        .distance(DISTANCE)
                                        .build()
                                )
                                .build())
                )
                .build();
    }
}
