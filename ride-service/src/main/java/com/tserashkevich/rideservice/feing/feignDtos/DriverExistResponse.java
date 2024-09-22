package com.tserashkevich.rideservice.feing.feignDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverExistResponse {
    private Boolean exist;
}
