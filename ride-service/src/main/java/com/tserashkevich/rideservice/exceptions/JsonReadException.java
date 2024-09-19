package com.tserashkevich.rideservice.exceptions;

import com.tserashkevich.rideservice.utils.ExceptionList;

public class JsonReadException extends RuntimeException {
    public JsonReadException() {
        super(ExceptionList.JSON_READ_EXCEPTION.getValue());
    }
}
