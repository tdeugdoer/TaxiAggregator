package com.tserashkevich.passengerservice.services.impl;

import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.exceptions.PassengerNotFoundException;
import com.tserashkevich.passengerservice.mappers.PassengerMapper;
import com.tserashkevich.passengerservice.models.Passenger;
import com.tserashkevich.passengerservice.models.enums.Gender;
import com.tserashkevich.passengerservice.repositories.PassengerRepository;
import com.tserashkevich.passengerservice.services.PassangerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassangerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponse create(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toModel(passengerRequest);

        //Passenger passenger = new Passenger("Yahor", Gender.Men, "375292078876", LocalDate.now());
        passengerRepository.save(passenger);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponse update(UUID id, PassengerRequest passengerRequest) {
        Passenger passenger = getOrThrow(id);
        passengerMapper.updateModel(passenger, passengerRequest);
        passengerRepository.save(passenger);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public void delete(UUID id) {
        Passenger passenger = getOrThrow(id);
        passengerRepository.delete(passenger);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PassengerResponse> findAll() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengerMapper.toResponses(passengers);
    }

    @Transactional(readOnly = true)
    @Override
    public PassengerResponse findOne(UUID id) {
        Passenger passenger = getOrThrow(id);
        return passengerMapper.toResponse(passenger);
    }

    public Passenger getOrThrow(UUID id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        return optionalPassenger.orElseThrow(PassengerNotFoundException::new);
    }

}
