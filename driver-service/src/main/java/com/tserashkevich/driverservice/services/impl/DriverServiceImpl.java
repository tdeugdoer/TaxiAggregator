package com.tserashkevich.driverservice.services.impl;

import com.querydsl.core.types.Predicate;
import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.DriverRequest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.exceptions.DriverNotFoundException;
import com.tserashkevich.driverservice.mappers.CarMapper;
import com.tserashkevich.driverservice.mappers.DriverMapper;
import com.tserashkevich.driverservice.models.Car;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.models.QDriver;
import com.tserashkevich.driverservice.models.enums.Gender;
import com.tserashkevich.driverservice.repositories.DriverRepository;
import com.tserashkevich.driverservice.services.DriverService;
import com.tserashkevich.driverservice.utils.LogList;
import com.tserashkevich.driverservice.utils.QPredicates;
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
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final CarMapper carMapper;

    @Override
    public DriverResponse create(DriverRequest driverRequest) {
        Driver driver = driverMapper.toModel(driverRequest);
        driverRepository.save(driver);
        log.info(LogList.CREATE_DRIVER, driver.getId());
        return driverMapper.toResponse(driver);
    }

    @Override
    public DriverResponse update(UUID driverId, DriverRequest driverRequest) {
        Driver driver = getOrThrow(driverId);
        driverMapper.updateModel(driver, driverRequest);
        driverRepository.save(driver);
        log.info(LogList.EDIT_DRIVER, driverId);
        return driverMapper.toResponse(driver);
    }

    @Override
    public void delete(UUID driverId) {
        driverRepository.delete(getOrThrow(driverId));
        log.info(LogList.DELETE_DRIVER, driverId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<DriverResponse> findAll(int page, int limit, Sort sort, Gender gender, LocalDate birthDateStart, LocalDate birthDateEnd, Boolean available) {
        Pageable pageable = PageRequest.of(page, limit, sort);
        Predicate predicate = QPredicates.builder()
                .add(gender, QDriver.driver.gender::eq)
                .add(birthDateEnd, QDriver.driver.birthDate::before)
                .add(birthDateStart, QDriver.driver.birthDate::after)
                .add(available, QDriver.driver.available::eq)
                .build();
        if (predicate == null) {
            predicate = QDriver.driver.id.isNotNull();
        }
        Page<Driver> driverPage = driverRepository.findAll(predicate, pageable);
        List<DriverResponse> driverResponses = driverMapper.toResponses(driverPage.getContent());
        log.info(LogList.FIND_ALL_DRIVERS);
        return PageResponse.<DriverResponse>builder()
                .objectList(driverResponses)
                .totalElements(driverPage.getTotalElements())
                .totalPages(driverPage.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public DriverResponse findOne(UUID driverId) {
        Driver driver = getOrThrow(driverId);
        log.info(LogList.FIND_DRIVER, driverId);
        return driverMapper.toResponse(driver);
    }

    @Override
    public DriverResponse addCar(UUID driverId, CarRequest carRequest) {
        Driver driver = getOrThrow(driverId);
        Car car = carMapper.toModel(carRequest);
        driver.setCar(car);
        driverRepository.save(driver);
        return driverMapper.toResponse(driver);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existByPhoneNumber(String phoneNumber) {
        return driverRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public DriverResponse changeAvailableStatus(UUID driverId, Boolean available) {
        Driver driver = getOrThrow(driverId);
        driver.setAvailable(available);
        driverRepository.save(driver);
        return driverMapper.toResponse(driver);
    }

    public Driver getOrThrow(UUID driverId) {
        Optional<Driver> optionalCar = driverRepository.findById(driverId);
        return optionalCar.orElseThrow(DriverNotFoundException::new);
    }
}
