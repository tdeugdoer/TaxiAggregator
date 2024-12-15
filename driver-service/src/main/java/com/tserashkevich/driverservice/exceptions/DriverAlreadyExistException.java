package com.tserashkevich.driverservice.exceptions;

import com.tserashkevich.driverservice.utils.ExceptionList;

public class DriverAlreadyExistException extends RuntimeException {
    public DriverAlreadyExistException() {
        super(ExceptionList.PASSENGER_ALREADY_EXIST.getValue());
    }
}
