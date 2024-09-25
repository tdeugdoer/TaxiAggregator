package com.tserashkevich.driverservice.services;

import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.models.Car;
import com.tserashkevich.driverservice.models.Driver;

import java.util.List;

public interface CarService {
    CarResponse create(CarRequest carRequest);

    List<Car> create(Driver driverId, List<CarWithoutDriverRequest> carRequests);

    CarResponse update(Long carId, CarRequest carRequest);

    void delete(Long carId);

    PageResponse<CarResponse> findAll(CarFindAllParams carFindAllParams);

    CarResponse findById(Long carId);

    Boolean existById(Long carId);

    Boolean existByCarNumber(String carNumber);
}