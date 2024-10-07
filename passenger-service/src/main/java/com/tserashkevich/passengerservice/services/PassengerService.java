package com.tserashkevich.passengerservice.services;

import com.tserashkevich.passengerservice.dtos.FindAllParams;
import com.tserashkevich.passengerservice.dtos.PageResponse;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;

import java.util.UUID;

public interface PassengerService {
    PassengerResponse create(PassengerRequest passengerRequest);

    PassengerResponse update(UUID id, PassengerRequest passengerRequest);

    void delete(UUID passengerId);

    PageResponse<PassengerResponse> findAll(FindAllParams findAllParams);

    PassengerResponse findById(UUID passengerId);

    Boolean existById(UUID passengerId);

    Boolean existByPhoneNumber(String phoneNumber);
}
