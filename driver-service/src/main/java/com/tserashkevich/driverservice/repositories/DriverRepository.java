package com.tserashkevich.driverservice.repositories;

import com.tserashkevich.driverservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID>, QuerydslPredicateExecutor<Driver> {
    Boolean existsByPhoneNumber(String phoneNumber);
}
