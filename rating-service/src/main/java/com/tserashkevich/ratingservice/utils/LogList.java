package com.tserashkevich.ratingservice.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogList {
    public static final String NOT_FOUND_ERROR = "Not Found exception thrown: {}";
    public static final String METHOD_ARGUMENT_ERROR = "Not Valid Method Argument exception thrown: {}";
    public static final String CONSTRAINT_VIOLATION_ERROR = "Failed parameter verification exception thrown: {}";
    public static final String CREATE_RATING = "Rating created with ID: {}";
    public static final String EDIT_RATING = "Rating edited with ID: {}";
    public static final String DELETE_RATING = "Rating deleted with ID: {}";
    public static final String FIND_ALL_RATINGS = "Found all ratings";
    public static final String FIND_RATING = "Found Rating with ID: {}";
}