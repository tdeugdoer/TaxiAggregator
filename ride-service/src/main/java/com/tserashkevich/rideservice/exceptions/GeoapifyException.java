package com.tserashkevich.rideservice.exceptions;

import com.tserashkevich.rideservice.utils.ExceptionList;

public class GeoapifyException extends RuntimeException {
    public GeoapifyException() {
        super(ExceptionList.GEOAPIFY_EXCEPTION.getValue());
    }
    public GeoapifyException(String message) {
        super(message);
    }
}
