package com.tserashkevich.driverservice.exceptions;


import com.tserashkevich.driverservice.utils.ExceptionList;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException() {
        super(ExceptionList.CAR_NOT_FOUND.getValue());
    }
}
