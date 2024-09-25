package com.tserashkevich.driverservice.dtos;

import com.tserashkevich.driverservice.models.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Getter
@Builder
@RequiredArgsConstructor
public class DriverFindAllParams {
    private final Integer page;
    private final Integer limit;
    private final Sort sort;
    private final Gender gender;
    private final LocalDate birthDateStart;
    private final LocalDate birthDateEnd;
    private final Boolean avaulable;
}
