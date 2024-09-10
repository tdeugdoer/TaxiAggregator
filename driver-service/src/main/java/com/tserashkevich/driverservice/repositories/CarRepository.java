package com.tserashkevich.driverservice.repositories;

import com.tserashkevich.driverservice.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, QuerydslPredicateExecutor<Car> {
    Boolean existsByNumber(String carNumber);
}
