package com.tserashkevich.driverservice.feign.feignDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvgRatingsResponse {
    private Double avgRating;
}

