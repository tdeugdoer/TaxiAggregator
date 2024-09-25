package com.tserashkevich.driverservice.exceptions.feign;


import com.tserashkevich.driverservice.utils.ExceptionList;

public class OtherServiceServerException extends RuntimeException {
    public OtherServiceServerException() {
        super(ExceptionList.SERVER_OTHER_SERVICE.getValue());
    }
}
