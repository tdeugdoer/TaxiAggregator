package com.tserashkevich.driverservice.services;

import com.tserashkevich.driverservice.dtos.DriverRequest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.DriverUpdateRequest;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.models.enums.Gender;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.UUID;

public interface DriverService {
    DriverResponse create(DriverRequest driverRequest);
    DriverResponse update(UUID driverId, DriverUpdateRequest driverUpdateRequest);
    void delete(UUID driverId);
    PageResponse<DriverResponse> findAll(int page, int limit, Sort sort, Gender gender,
                                         LocalDate birthDateStart, LocalDate birthDateEnd, Boolean available);
    DriverResponse findById(UUID driverId);
    Boolean existByPhoneNumber(String phoneNumber);
    Boolean existById(UUID driverId);
    DriverResponse changeAvailableStatus(UUID driverId);
}
