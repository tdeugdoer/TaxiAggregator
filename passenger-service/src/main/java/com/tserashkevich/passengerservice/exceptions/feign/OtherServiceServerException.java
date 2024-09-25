package com.tserashkevich.passengerservice.exceptions.feign;


import com.tserashkevich.passengerservice.utils.ExceptionList;

public class OtherServiceServerException extends RuntimeException {
    public OtherServiceServerException() {
        super(ExceptionList.SERVER_OTHER_SERVICE.getValue());
    }
}
