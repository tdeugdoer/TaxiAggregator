package com.tserashkevich.driverservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DriverResponse {
    private final UUID id;
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final LocalDate birthDate;
    private final Boolean available;
    private final Double avgRating;
    private final List<CarWithoutDriverResponse> cars;
}
