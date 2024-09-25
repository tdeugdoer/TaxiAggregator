package com.tserashkevich.driverservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogList {
    public static final String NOT_FOUND_ERROR = "Not Found exception thrown: {}";
    public static final String METHOD_ARGUMENT_ERROR = "Not Valid Method Argument exception thrown: {}";
    public static final String CONSTRAINT_VIOLATION_ERROR = "Failed parameter verification exception thrown: {}";
    public static final String CREATE_DRIVER = "Driver created with ID: {}";
    public static final String EDIT_DRIVER = "Driver edited with ID: {}";
    public static final String DELETE_DRIVER = "Driver deleted with ID: {}";
    public static final String FIND_ALL_DRIVERS = "Found all drivers";
    public static final String FIND_DRIVER = "Found Driver with ID: {}";
    public static final String CREATE_CAR = "Car created with ID: {}";
    public static final String CREATE_CARS = "Cars created with IDs: {}";
    public static final String EDIT_CAR = "Car edited with ID: {}";
    public static final String DELETE_CAR = "Car deleted with ID: {}";
    public static final String FIND_ALL_CARS = "Found all cars";
    public static final String FIND_CAR = "Found Car with ID: {}";
    public static final String BAD_REQUEST_OTHER_SERVICE = "Bad request, other service: {}";
    public static final String NOT_FOUND_OTHER_SERVICE = "Not found, other service: {}";
    public static final String SERVER_OTHER_SERVICE = "Server, other service: {}";
    public static final String EXIST_CAR_BY_ID = "Exist car by id: {}";
    public static final String EXIST_CAR_BY_NUMBER = "Exist car by number: {}";
    public static final String CHANGE_DRIVER_STATUS = "Change driver available status: {}";
    public static final String EXIST_DRIVER_BY_ID = "Exist driver by id: {}";
    public static final String EXIST_DRIVER_BY_PHONE_NUMBER = "Exist driver by phone number: {}";
    public static final String CIRCUITBREAKER_OPEN = "CircuitBreaker is open: {}";
}