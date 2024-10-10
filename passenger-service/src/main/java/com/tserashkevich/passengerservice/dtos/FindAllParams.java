package com.tserashkevich.passengerservice.dtos;

import com.tserashkevich.passengerservice.models.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Getter
@Builder
public class FindAllParams {
    private final Integer page;
    private final Integer limit;
    private final Sort sort;
    private final Gender gender;
    private final LocalDate birthDateStart;
    private final LocalDate birthDateEnd;
}