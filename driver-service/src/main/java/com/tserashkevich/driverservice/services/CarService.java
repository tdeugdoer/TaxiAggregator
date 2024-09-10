package com.tserashkevich.driverservice.services;

import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.models.enums.Color;
import org.springframework.data.domain.Sort;

public interface CarService {
    CarResponse create(CarRequest carRequest);
    CarResponse update(Long carId, CarRequest carRequest);
    void delete(Long carId);
    PageResponse<CarResponse> findAll(int page, int limit, Sort sort, String number, String brand, String model, Color color);
    CarResponse findById(Long carId);
    Boolean existByCarNumber(String carNumber);
}