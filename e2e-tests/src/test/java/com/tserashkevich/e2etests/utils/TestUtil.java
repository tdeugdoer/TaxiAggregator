package com.tserashkevich.e2etests.utils;

import com.tserashkevich.e2etests.dtos.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class TestUtil {
    public final String GENDER = "Men";
    public final LocalDate BIRTH_DATE = LocalDate.of(2000, 9, 20);
    public final String BRAND = "Mercedes";
    public final String MODEL = "c300";
    public final String COLOR = "Black";
    public final String RIDE_NOT_FINISHED_MESSAGE = "Ride not finished";
    public final String BASE_URL = "http://localhost:";
    public final String PASSENGERS_PATH = "/api/v1/passengers";
    public final String DRIVERS_PATH = "/api/v1/drivers";
    public final String RIDES_PATH = "/api/v1/rides";
    public final String RATINGS_PATH = "/api/v1/ratings";
    public final Integer PASSENGERS_PORT = 8081;
    public final Integer DRIVERS_PORT = 8080;
    public final Integer RIDES_PORT = 8082;
    public final Integer RATINGS_PORT = 8083;

    public PassengerRequest getPassengerRequest(String name, String phone) {
        return PassengerRequest.builder()
                .name(name)
                .phoneNumber(phone)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .build();
    }

    public DriverRequest getDriverRequest(String name, String phone, String carNumber) {
        return DriverRequest.builder()
                .name(name)
                .phoneNumber(phone)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .cars(List.of(CarWithoutDriverRequest.builder()
                                .number(carNumber)
                                .brand(BRAND)
                                .model(MODEL)
                                .color(COLOR)
                                .build()
                        )
                )
                .build();
    }

    public CreateRideRequest getCreateRideRequest(String passengerId, String startGeoPoint, String endGeoPoint) {
        return CreateRideRequest.builder()
                .passengerId(passengerId)
                .startGeoPoint(startGeoPoint)
                .endGeoPoint(endGeoPoint)
                .build();
    }

    public CreateRatingRequest getCreateRatingRequest(String rideId, String comment, String rating) {
        return CreateRatingRequest.builder()
                .rideId(rideId)
                .comment(comment)
                .rating(Integer.parseInt(rating))
                .build();
    }
}
