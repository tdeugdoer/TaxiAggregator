package com.tserashkevich.passengerservice.configs.feign;

import com.tserashkevich.passengerservice.exceptions.feign.OtherServiceBadRequestException;
import com.tserashkevich.passengerservice.exceptions.feign.OtherServiceNotFoundException;
import com.tserashkevich.passengerservice.exceptions.feign.OtherServiceServerException;
import com.tserashkevich.passengerservice.utils.LogList;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);
        int status = response.status();
        if (status == HttpStatus.BAD_REQUEST.value()) {
            log.error(LogList.BAD_REQUEST_OTHER_SERVICE, exception.getMessage());
            return new OtherServiceBadRequestException();
        } else if (status == HttpStatus.NOT_FOUND.value()) {
            log.error(LogList.NOT_FOUND_OTHER_SERVICE, exception.getMessage());
            return new OtherServiceNotFoundException();
        } else if (status >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error(LogList.SERVER_OTHER_SERVICE, exception.getMessage());
            return new OtherServiceServerException();
        }
        return exception;
    }
}
