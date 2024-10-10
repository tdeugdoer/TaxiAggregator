package com.tserashkevich.driverservice.dtos;

import com.tserashkevich.driverservice.models.enums.Color;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
@Builder
public class CarFindAllParams {
    private final Integer page;
    private final Integer limit;
    private final Sort sort;
    private final String number;
    private final String brand;
    private final String model;
    private final Color color;
}