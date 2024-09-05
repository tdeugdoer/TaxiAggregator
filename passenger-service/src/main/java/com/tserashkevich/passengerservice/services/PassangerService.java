package com.tserashkevich.passengerservice.services;

import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;

import java.util.List;
import java.util.UUID;

public interface PassangerService {

    PassengerResponse create(PassengerRequest passengerRequest);
    PassengerResponse update(UUID id, PassengerRequest passengerRequest);
    void delete(UUID id);
    List<PassengerResponse> findAll();
    PassengerResponse findOne(UUID id);

}
