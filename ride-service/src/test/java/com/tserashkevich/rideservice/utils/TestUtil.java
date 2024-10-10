package com.tserashkevich.rideservice.utils;

import com.tserashkevich.rideservice.dtos.*;
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
import java.util.UUID;

@UtilityClass
public class TestUtil {
    public final String RIDE_ID = "6592008029c8c3e4dc76256c";
    public final String SECOND_RIDE_ID = "507f1f77bcf86cd799439011";
    public final UUID PASSENGER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public final UUID SECOND_PASSENGER_ID = UUID.fromString("22222222-1111-1111-1111-222222222222");
    public final UUID DRIVER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public final UUID SECOND_DRIVER_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public final Long CAR_ID = 1L;
    public final Long SECOND_CAR_ID = 2L;
    public final String START_GEO_POINT = "52.093865|23.706679";
    public final String SECOND_START_GEO_POINT = "54.506823|27.100678";
    public final String START_GEO_POINT_NAME = "Belarus, Brestskaya Voblasts', 224000 Brest, вуліца Гогаля, 83";
    public final String SECOND_START_GEO_POINT_NAME = "Belarus, Minsk Region, 222440 Крывасельскі сельскі Савет, Н22121";
    public final String END_GEO_POINT = "52.099302|23.699555";
    public final String SECOND_END_GEO_POINT = "54.027093|27.273479";
    public final String END_GEO_POINT_NAME = "Чыгуначны каледж, Belarus, Brestskaya Voblasts', 224000 Brest";
    public final String SECOND_END_GEO_POINT_NAME = "Belarus, Minsk, 223036, Ул. Молодечненская, 7";
    public final LocalDateTime START_TIME = LocalDateTime.of(2024, 9, 29, 5, 23, 12);
    public final LocalDateTime SECOND_START_TIME = LocalDateTime.of(2024, 10, 1, 14, 40, 54);
    public final LocalDateTime SECOND_END_TIME = LocalDateTime.now();
    public final Status STATUS_CREATED = Status.CREATED;
    public final String STATUS_CREATED_NAME = Status.CREATED.name();
    public final Status STATUS_FINISHED = Status.FINISHED;
    public final String STATUS_FINISHED_NAME = Status.FINISHED.name();
    public final Integer DISTANCE = 100;
    public final Integer SECOND_DISTANCE = 50;
    public final String COMMENT = "Комментарий";
    public final Integer RATING = 5;
    public final Integer PAGE = 0;
    public final Integer LIMIT = 10;
    public final Sort SORT = Sort.by(Sort.Direction.ASC, "id");

    public Ride getRide() {
        return Ride.builder()
                .id(RIDE_ID)
                .startAddress(Address.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_GEO_POINT)
                        .build())
                .endAddress(Address.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_GEO_POINT)
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
                        .geoPoint(SECOND_START_GEO_POINT)
                        .build())
                .endAddress(Address.builder()
                        .name(SECOND_END_GEO_POINT_NAME)
                        .geoPoint(SECOND_END_GEO_POINT)
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
                        .geoPoint(START_GEO_POINT)
                        .build())
                .endAddress(Address.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_GEO_POINT)
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
                .startGeoPoint(START_GEO_POINT)
                .endGeoPoint(END_GEO_POINT)
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
                .rideId(SECOND_RIDE_ID)
                .comment(COMMENT)
                .rating(RATING)
                .build();
    }

    public RideResponse getRideResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_GEO_POINT)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_GEO_POINT)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .build())
                .status(STATUS_CREATED_NAME)
                .build();
    }

    public RideResponse getRideWithDriverResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .driverId(DRIVER_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_GEO_POINT)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_GEO_POINT)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .build())
                .status(STATUS_CREATED_NAME)
                .build();
    }

    public RideResponse getRideWithCarResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .carId(CAR_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_GEO_POINT)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_GEO_POINT)
                        .build())
                .distance(DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .build())
                .status(STATUS_CREATED_NAME)
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
                        .geoPoint(SECOND_START_GEO_POINT)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(SECOND_END_GEO_POINT_NAME)
                        .geoPoint(SECOND_END_GEO_POINT)
                        .build())
                .distance(SECOND_DISTANCE)
                .time(TimeResponse.builder()
                        .startTime(START_TIME)
                        .endTime(LocalDateTime.now())
                        .build())
                .status(STATUS_FINISHED_NAME)
                .build();
    }

    public CreateRideResponse getCreateRideResponse() {
        return CreateRideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .startAddress(AddressResponse.builder()
                        .name(START_GEO_POINT_NAME)
                        .geoPoint(START_GEO_POINT)
                        .build())
                .endAddress(AddressResponse.builder()
                        .name(END_GEO_POINT_NAME)
                        .geoPoint(END_GEO_POINT)
                        .build())
                .distance(DISTANCE)
                .startTime(START_TIME)
                .status(STATUS_CREATED_NAME)
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
                .page(TestUtil.PAGE)
                .limit(LIMIT)
                .sort(SORT)
                .build();
    }

    public Query getFindQueryPredicate() {
        return new Query().with(PageRequest.of(PAGE, LIMIT, SORT));
    }

    public Page<Ride> getPageOfRides() {
        return new PageImpl<>(getRides());
    }

    public PageResponse<RideResponse> getPageResponse(List<RideResponse> rideResponses) {
        Page<Ride> ridePage = getPageOfRides();
        return PageResponse.<RideResponse>builder()
                .objectList(rideResponses)
                .totalElements(ridePage.getTotalElements())
                .totalPages(ridePage.getTotalPages())
                .build();
    }
}
