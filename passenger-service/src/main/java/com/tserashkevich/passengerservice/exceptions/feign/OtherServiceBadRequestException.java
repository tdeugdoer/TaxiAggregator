package com.tserashkevich.passengerservice.exceptions.feign;


import com.tserashkevich.passengerservice.utils.ExceptionList;

public class OtherServiceBadRequestException extends RuntimeException {
    public OtherServiceBadRequestException() {
        super(ExceptionList.BAD_REQUEST_OTHER_SERVICE.getValue());
    }
}
