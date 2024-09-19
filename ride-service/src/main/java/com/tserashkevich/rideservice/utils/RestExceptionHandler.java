package com.tserashkevich.rideservice.utils;

import com.tserashkevich.rideservice.dtos.ExceptionResponse;
import com.tserashkevich.rideservice.dtos.ValidationErrorResponse;
import com.tserashkevich.rideservice.dtos.Violation;
import com.tserashkevich.rideservice.exceptions.GeoapifyException;
import com.tserashkevich.rideservice.exceptions.JsonReadException;
import com.tserashkevich.rideservice.exceptions.RideNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        log.error(LogList.NOT_FOUND_ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<Violation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .toList();
        log.error(LogList.METHOD_ARGUMENT_ERROR, violations);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationErrorResponse(violations));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(RuntimeException ex) {
        log.error(LogList.CONSTRAINT_VIOLATION_ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(LogList.METHOD_ARGUMENT_ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse("Wrong request parameter: " + ex.getName()));
    }

    @ExceptionHandler(GeoapifyException.class)
    public ResponseEntity<ExceptionResponse> handleGeoapifyException(GeoapifyException ex) {
        log.error(LogList.GEOAPI_ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse("Geoapify exception: " + ex.getMessage()));

    }

    @ExceptionHandler(JsonReadException.class)
    public ResponseEntity<ExceptionResponse> handleJsonReadException(JsonReadException ex) {
        log.error(LogList.JSON_READ_ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ExceptionList.JSON_READ_EXCEPTION.getValue()));
    }
}
