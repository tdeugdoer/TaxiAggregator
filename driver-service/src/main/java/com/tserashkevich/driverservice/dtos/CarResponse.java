package com.tserashkevich.driverservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarResponse {
    private final Long id;
    private final String number;
    private final String brand;
    private final String model;
    private final String color;
}
