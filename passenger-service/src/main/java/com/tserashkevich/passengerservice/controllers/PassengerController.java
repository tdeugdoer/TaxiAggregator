package com.tserashkevich.passengerservice.controllers;

import com.tserashkevich.passengerservice.dtos.*;
import com.tserashkevich.passengerservice.models.enums.Gender;
import com.tserashkevich.passengerservice.services.PassengerService;
import com.tserashkevich.passengerservice.utils.SortList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse createPassenger(@Valid @RequestBody PassengerRequest passengerRequest) {
        return passengerService.create(passengerRequest);
    }

    @PutMapping("/{id}")
    public PassengerResponse updatePassenger(@PathVariable UUID id, @Valid @RequestBody PassengerRequest passengerRequest) {
        return passengerService.update(id, passengerRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassenger(@PathVariable UUID id) {
        passengerService.delete(id);
    }

    @GetMapping
    public PageResponse<PassengerResponse> findAllPassengers(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                             @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                             @RequestParam(defaultValue = "ID_ASC") SortList sort,
                                                             @RequestParam(required = false) Gender gender,
                                                             @RequestParam(required = false) LocalDate birthDateStart,
                                                             @RequestParam(required = false) LocalDate birthDateEnd) {
        FindAllParams findAllParams = FindAllParams.builder()
                .page(page)
                .limit(limit)
                .sort(sort.getValue())
                .gender(gender)
                .birthDateStart(birthDateStart)
                .birthDateEnd(birthDateEnd)
                .build();
        return passengerService.findAll(findAllParams);
    }

    @GetMapping("/{id}")
    public PassengerResponse findPassengerById(@PathVariable UUID id) {
        return passengerService.findById(id);
    }

    @GetMapping("/exist/{passengerId}")
    public PassengerExistResponse existPassenger(@PathVariable UUID passengerId) {
        return passengerService.existById(passengerId);
    }
}
