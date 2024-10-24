package com.tserashkevich.passengerservice.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class PassengerResponse {
    private final UUID id;
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final LocalDate birthDate;
    private final Double avgRating;
}
