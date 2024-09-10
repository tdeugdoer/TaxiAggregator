package com.tserashkevich.driverservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DriverResponse {
    private final UUID id;
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final LocalDate birthDate;
}
