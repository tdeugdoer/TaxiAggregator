package com.tserashkevich.driverservice.exceptions.feign;


import com.tserashkevich.driverservice.utils.ExceptionList;

public class OtherServiceBadRequestException extends RuntimeException {
    public OtherServiceBadRequestException() {
        super(ExceptionList.BAD_REQUEST_OTHER_SERVICE.getValue());
    }
}
