package com.tserashkevich.e2etests.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DriverRequest {
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final LocalDate birthDate;
    private final List<CarWithoutDriverRequest> cars;
}