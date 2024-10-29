package com.tserashkevich.e2etests.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PassengerRequest {
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final LocalDate birthDate;
}
