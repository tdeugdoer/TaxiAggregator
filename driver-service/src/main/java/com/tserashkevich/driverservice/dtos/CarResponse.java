package com.tserashkevich.driverservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CarResponse {
    private final Long id;
    private final String number;
    private final String brand;
    private final String model;
    private final String color;
    private final UUID driver;
}
