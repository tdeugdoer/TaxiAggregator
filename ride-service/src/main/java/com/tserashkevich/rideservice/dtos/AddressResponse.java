package com.tserashkevich.rideservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddressResponse {
    private final String geoPoint;
    private final String name;
}
