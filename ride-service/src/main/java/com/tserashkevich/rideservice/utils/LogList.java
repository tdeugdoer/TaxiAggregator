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
    public static final String BAD_REQUEST_OTHER_SERVICE = "Bad request, other service: {}";
    public static final String NOT_FOUND_OTHER_SERVICE = "Not found, other service: {}";
    public static final String SERVER_OTHER_SERVICE = "Server, other service: {}";
    public static final String CHANGE_STATUS = "Change status. Ride id: {}";
    public static final String CHANGE_DRIVER = "Change driver. Ride id: {}";
    public static final String CHANGE_CAR = "Change car. Ride id: {}";
    public static final String CIRCUITBREAKER_OPEN = "CircuitBreaker is open: {}";
}