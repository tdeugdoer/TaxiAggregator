package com.tserashkevich.passengerservice.repositories;

import com.tserashkevich.passengerservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID>, QuerydslPredicateExecutor<Passenger> {
    Boolean existsByPhoneNumber(String phoneNumber);
}
