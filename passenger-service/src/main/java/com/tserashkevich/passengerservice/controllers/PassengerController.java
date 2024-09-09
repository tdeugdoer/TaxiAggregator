package com.tserashkevich.passengerservice.controllers;

import com.tserashkevich.passengerservice.dtos.PageResponse;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.models.enums.Gender;
import com.tserashkevich.passengerservice.services.PassengerService;
import com.tserashkevich.passengerservice.utils.SortList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse createProduct(@Valid @RequestBody PassengerRequest passengerRequest) {
        return passengerService.create(passengerRequest);
    }

    @PutMapping("/{id}")
    public PassengerResponse updateProduct(@PathVariable UUID id, @RequestBody PassengerRequest passengerRequest) {
        return passengerService.update(id, passengerRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        passengerService.delete(id);
    }

    @GetMapping
    public PageResponse<PassengerResponse> findAllProducts(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                           @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                           @RequestParam(defaultValue = "ID_ASC") SortList sort,
                                                           @RequestParam(required = false) Gender gender,
                                                           @RequestParam(required = false) LocalDate birthDateStart,
                                                           @RequestParam(required = false) LocalDate birthDateEnd) {
        return passengerService.findAll(page, limit, sort.getValue(), gender, birthDateStart, birthDateEnd);
    }

    @GetMapping("/{id}")
    public PassengerResponse findProductById(@PathVariable UUID id) {
        return passengerService.findOne(id);
    }
}
