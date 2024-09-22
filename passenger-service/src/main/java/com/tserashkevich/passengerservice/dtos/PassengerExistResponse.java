package com.tserashkevich.passengerservice.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PassengerExistResponse {
    private final Boolean exist;
}
