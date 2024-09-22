package com.tserashkevich.passengerservice.exceptions;

import com.tserashkevich.passengerservice.utils.ExceptionList;

public class BadRequestToOtherServiceException extends RuntimeException {
    public BadRequestToOtherServiceException(String message) {
        super(String.format("%s: %s", ExceptionList.BAD_REQUEST_TO_OTHER_SERVICE.getValue(), message));
    }
}
