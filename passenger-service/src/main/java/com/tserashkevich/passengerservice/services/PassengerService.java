package com.tserashkevich.passengerservice.services;

import com.tserashkevich.passengerservice.dtos.PageResponse;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.models.enums.Gender;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.UUID;

public interface PassengerService {
    PassengerResponse create(PassengerRequest passengerRequest);
    PassengerResponse update(UUID id, PassengerRequest passengerRequest);
    void delete(UUID id);
    PageResponse<PassengerResponse> findAll(int page, int limit, Sort sort, Gender gender, LocalDate birthDateStart, LocalDate birthDateEnd);
    PassengerResponse findOne(UUID id);
    Boolean existByName(String name);
    Boolean existByPhoneNumber(String name);
}
