package com.tserashkevich.rideservice.exceptions.feign;


import com.tserashkevich.rideservice.utils.ExceptionList;

public class OtherServiceServerException extends RuntimeException {
    public OtherServiceServerException() {
        super(ExceptionList.SERVER_OTHER_SERVICE.getValue());
    }
}
