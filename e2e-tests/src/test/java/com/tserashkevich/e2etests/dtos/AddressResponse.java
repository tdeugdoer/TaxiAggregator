package com.tserashkevich.e2etests.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponse {
    private final String geoPoint;
    private final String name;
}
