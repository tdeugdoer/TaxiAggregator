package com.tserashkevich.driverservice.services;

import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.DriverRequest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.models.enums.Gender;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.UUID;

public interface DriverService {
    DriverResponse create(DriverRequest driverRequest);
    DriverResponse update(UUID driverId, DriverRequest driverRequest);
    void delete(UUID driverId);
    PageResponse<DriverResponse> findAll(int page, int limit, Sort sort, Gender gender,
                                         LocalDate birthDateStart, LocalDate birthDateEnd, Boolean available);
    DriverResponse findById(UUID driverId);
    DriverResponse addCar(UUID driverId, CarRequest carRequest);
    Boolean existByPhoneNumber(String phoneNumber);
    DriverResponse changeAvailableStatus(UUID driverId, Boolean available);
}
