package com.tserashkevich.passengerservice.exceptions;

import com.tserashkevich.passengerservice.utils.ExceptionList;

public class PassengerAlreadyExistException extends RuntimeException {
    public PassengerAlreadyExistException() {
        super(ExceptionList.PASSENGER_ALREADY_EXIST.getValue());
    }
}
