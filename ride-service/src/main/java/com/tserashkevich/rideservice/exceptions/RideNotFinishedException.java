package com.tserashkevich.rideservice.exceptions;

import com.tserashkevich.rideservice.utils.ExceptionList;

public class RideNotFinishedException extends RuntimeException {
    public RideNotFinishedException() {
        super(ExceptionList.RIDE_NOT_FINISHED.getValue());
    }

}
