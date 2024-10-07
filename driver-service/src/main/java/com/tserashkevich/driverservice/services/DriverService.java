package com.tserashkevich.driverservice.services;

import com.tserashkevich.driverservice.dtos.*;

import java.util.UUID;

public interface DriverService {
    DriverResponse create(DriverRequest driverRequest);

    DriverResponse update(UUID driverId, DriverUpdateRequest driverUpdateRequest);

    void delete(UUID driverId);

    PageResponse<DriverResponse> findAll(DriverFindAllParams driverFindAllParams);

    DriverResponse findById(UUID driverId);

    Boolean existByPhoneNumber(String phoneNumber);

    Boolean existById(UUID driverId);

    DriverResponse changeAvailableStatus(UUID driverId, Boolean available);
}
