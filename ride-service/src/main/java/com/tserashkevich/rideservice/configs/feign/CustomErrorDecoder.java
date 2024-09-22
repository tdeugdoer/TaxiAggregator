package com.tserashkevich.rideservice.configs.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tserashkevich.rideservice.dtos.FeignExceptionResponse;
import com.tserashkevich.rideservice.exceptions.BadRequestToOtherServiceException;
import com.tserashkevich.rideservice.utils.LogList;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorDecoder defaultErrorDecoder = new Default();

    public CustomErrorDecoder() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream bodyInputStream = response.body().asInputStream()) {
            FeignExceptionResponse exceptionResponse = objectMapper.readValue(bodyInputStream, FeignExceptionResponse.class);
            if (exceptionResponse.getHttpStatus().is5xxServerError()) {
                return new RetryableException(
                        response.status(),
                        exceptionResponse.getMessage(),
                        response.request().httpMethod(),
                        FeignException.errorStatus(methodKey, response),
                        (Long) null,
                        response.request());
            }
            if (exceptionResponse.getHttpStatus().is4xxClientError()) {
                return new BadRequestToOtherServiceException(exceptionResponse.getMessage());
            }
        } catch (IOException ex) {
            log.error(LogList.LOG_DECODE_ERROR, ex.getMessage());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
