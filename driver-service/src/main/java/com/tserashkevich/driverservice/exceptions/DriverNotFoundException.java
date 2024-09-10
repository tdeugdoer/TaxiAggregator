package com.tserashkevich.driverservice.exceptions;


import com.tserashkevich.driverservice.utils.ExceptionList;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException() {
        super(ExceptionList.DRIVER_NOT_FOUND.getValue());
    }
}
