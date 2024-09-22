package com.tserashkevich.passengerservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogList {
    public static final String NOT_FOUND_ERROR = "Not Found exception thrown: {}";
    public static final String METHOD_ARGUMENT_ERROR = "Not Valid Method Argument exception thrown: {}";
    public static final String CONSTRAINT_VIOLATION_ERROR = "Failed parameter verification exception thrown: {}";
    public static final String CREATE_PASSENGER = "Passenger created with ID: {}";
    public static final String EDIT_PASSENGER = "Passenger edited with ID: {}";
    public static final String DELETE_PASSENGER = "Passenger deleted with ID: {}";
    public static final String FIND_ALL_PASSENGER = "Found all passengers";
    public static final String FIND_PASSENGER = "Found Passenger with ID: {}";
    public static final String DECODE_ERROR = "Error decoding response body: {}";
    public static final String BAD_REQUEST_TO_OTHER_SERVICE = "Bad request to other service: {}";
}