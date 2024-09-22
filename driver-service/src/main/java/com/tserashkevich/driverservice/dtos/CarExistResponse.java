package com.tserashkevich.driverservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarExistResponse {
    private final Boolean exist;
}
