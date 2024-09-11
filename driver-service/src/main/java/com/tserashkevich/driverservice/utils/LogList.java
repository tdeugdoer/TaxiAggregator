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
    public static final String EDIT_CAR = "Car edited with ID: {}";
    public static final String DELETE_CAR = "Car deleted with ID: {}";
    public static final String FIND_ALL_CARS = "Found all cars";
    public static final String FIND_CAR = "Found Car with ID: {}";
}