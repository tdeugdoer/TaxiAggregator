package com.tserashkevich.rideservice.exceptions;

import com.tserashkevich.rideservice.utils.ExceptionList;

public class RideNotFoundException extends RuntimeException {
    public RideNotFoundException() {
        super(ExceptionList.RIDE_NOT_FOUND.getValue());
    }
}
