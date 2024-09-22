package com.tserashkevich.passengerservice.utils;

import com.tserashkevich.passengerservice.dtos.ExceptionResponse;
import com.tserashkevich.passengerservice.dtos.ValidationErrorResponse;
import com.tserashkevich.passengerservice.dtos.Violation;
import com.tserashkevich.passengerservice.exceptions.BadRequestToOtherServiceException;
import com.tserashkevich.passengerservice.exceptions.PassengerNotFoundException;
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
    @ExceptionHandler(PassengerNotFoundException.class)
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

    @ExceptionHandler(BadRequestToOtherServiceException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestToOtherServiceException(RuntimeException ex) {
        log.error(LogList.BAD_REQUEST_TO_OTHER_SERVICE, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse("Wrong request parameter: " + ex.getMessage()));
    }
}
