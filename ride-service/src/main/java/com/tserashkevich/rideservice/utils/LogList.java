package com.tserashkevich.rideservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogList {
    public static final String NOT_FOUND_ERROR = "Not Found exception thrown: {}";
    public static final String METHOD_ARGUMENT_ERROR = "Not Valid Method Argument exception thrown: {}";
    public static final String CONSTRAINT_VIOLATION_ERROR = "Failed parameter verification exception thrown: {}";
    public static final String CREATE_RIDE = "Ride created with ID: {}";
    public static final String DELETE_RIDE = "Ride deleted with ID: {}";
    public static final String FIND_ALL_RIDES = "Found all rides";
    public static final String FIND_RIDE = "Found Ride with ID: {}";
    public static final String GEOAPI_ERROR = "Request error to geoapi";
    public static final String JSON_READ_ERROR = "Json read error";
    public static final String LOG_DECODE_ERROR = "Error decoding response body: {}";
    public static final String BAD_REQUEST_TO_OTHER_SERVICE = "Bad request to other service: {}";

}