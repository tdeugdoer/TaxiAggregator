package com.tserashkevich.e2etests.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarWithoutDriverRequest {
    private final String number;
    private final String brand;
    private final String model;
    private final String color;
}
