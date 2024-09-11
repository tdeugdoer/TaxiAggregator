package com.tserashkevich.driverservice.dtos;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Violation {
    private final String fieldName;
    private final String message;
    private final LocalDateTime timestamp;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
