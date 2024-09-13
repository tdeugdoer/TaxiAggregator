package com.tserashkevich.rideservice.models;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String geoPoint;
    private String name;
}
