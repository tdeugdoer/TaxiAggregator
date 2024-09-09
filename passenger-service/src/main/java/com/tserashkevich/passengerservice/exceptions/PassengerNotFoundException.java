package com.tserashkevich.passengerservice.exceptions;

import com.tserashkevich.passengerservice.utils.ExceptionList;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException() {
        super(ExceptionList.PASSENGER_NOT_FOUND.getValue());
    }
}
