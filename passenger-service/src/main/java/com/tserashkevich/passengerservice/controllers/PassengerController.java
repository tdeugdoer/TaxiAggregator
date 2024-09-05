package com.tserashkevich.passengerservice.controllers;

import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.services.PassangerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passengers")
public class PassengerController {

    private final PassangerService passangerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse createProduct(@RequestBody PassengerRequest passengerRequest) {
        return passangerService.create(passengerRequest);
    }

    @PutMapping("/{id}")
    public PassengerResponse updateProduct(@PathVariable UUID id, @RequestBody PassengerRequest passengerRequest) {
        return passangerService.update(id, passengerRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        passangerService.delete(id);
    }

    @GetMapping
    public List<PassengerResponse> findAllProducts() {
        return passangerService.findAll();
    }

    @GetMapping("/{id}")
    public PassengerResponse findProductById(@PathVariable UUID id) {
        return passangerService.findOne(id);
    }

}
