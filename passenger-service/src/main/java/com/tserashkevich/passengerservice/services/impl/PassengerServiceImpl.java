package com.tserashkevich.passengerservice.services.impl;


import com.querydsl.core.types.Predicate;
import com.tserashkevich.passengerservice.dtos.PageResponse;
import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.exceptions.PassengerNotFoundException;
import com.tserashkevich.passengerservice.mappers.PassengerMapper;
import com.tserashkevich.passengerservice.models.Passenger;
import com.tserashkevich.passengerservice.models.QPassenger;
import com.tserashkevich.passengerservice.models.enums.Gender;
import com.tserashkevich.passengerservice.repositories.PassengerRepository;
import com.tserashkevich.passengerservice.services.PassengerService;
import com.tserashkevich.passengerservice.utils.LogList;
import com.tserashkevich.passengerservice.utils.QPredicates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponse create(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toModel(passengerRequest);
        passengerRepository.save(passenger);
        log.info(LogList.CREATE_PASSENGER, passenger.getId());
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponse update(UUID id, PassengerRequest passengerRequest) {
        Passenger passenger = getOrThrow(id);
        passengerMapper.updateModel(passenger, passengerRequest);
        passengerRepository.save(passenger);
        log.info(LogList.EDIT_PASSENGER, id);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public void delete(UUID id) {
        passengerRepository.delete(getOrThrow(id));
        log.info(LogList.DELETE_PASSENGER, id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<PassengerResponse> findAll(int page, int limit, Sort sort, Gender gender, LocalDate birthDateStart, LocalDate birthDateEnd) {
        Pageable pageable = PageRequest.of(page, limit, sort);
        Predicate predicate = QPredicates.builder()
                .add(gender, QPassenger.passenger.gender::eq)
                .add(birthDateEnd, QPassenger.passenger.birthDate::before)
                .add(birthDateStart, QPassenger.passenger.birthDate::after)
                .build();
        if (predicate == null) {
            predicate = QPassenger.passenger.id.isNotNull();
        }
        Page<Passenger> passengerPage = passengerRepository.findAll(predicate, pageable);
        List<PassengerResponse> passengerResponses = passengerMapper.toResponses(passengerPage.getContent());
        log.info(LogList.FIND_ALL_PASSENGER);
        return PageResponse.<PassengerResponse>builder()
                .objectList(passengerResponses)
                .totalElements(passengerPage.getTotalElements())
                .totalPages(passengerPage.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public PassengerResponse findOne(UUID id) {
        Passenger passenger = getOrThrow(id);
        log.info(LogList.FIND_PASSENGER, id);
        return passengerMapper.toResponse(passenger);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existByName(String name) {
        return passengerRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existByPhoneNumber(String phoneNumber) {
        return passengerRepository.existsByPhoneNumber(phoneNumber);
    }

    public Passenger getOrThrow(UUID id) {
        Optional<Passenger> optionalPassenger = passengerRepository.findById(id);
        return optionalPassenger.orElseThrow(PassengerNotFoundException::new);
    }
}
