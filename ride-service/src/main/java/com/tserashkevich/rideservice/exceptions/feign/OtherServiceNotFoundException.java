package com.tserashkevich.rideservice.exceptions.feign;


import com.tserashkevich.rideservice.utils.ExceptionList;

public class OtherServiceNotFoundException extends RuntimeException {
    public OtherServiceNotFoundException() {
        super(ExceptionList.NOT_FOUND_OTHER_SERVICE.getValue());
    }
}
