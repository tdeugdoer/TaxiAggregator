package com.tserashkevich.driverservice.exceptions.feign;


import com.tserashkevich.driverservice.utils.ExceptionList;

public class OtherServiceNotFoundException extends RuntimeException {
    public OtherServiceNotFoundException() {
        super(ExceptionList.NOT_FOUND_OTHER_SERVICE.getValue());
    }
}
