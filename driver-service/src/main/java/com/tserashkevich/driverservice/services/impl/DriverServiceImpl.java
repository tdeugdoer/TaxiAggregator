package com.tserashkevich.driverservice.services.impl;

import com.querydsl.core.types.Predicate;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.exceptions.DriverNotFoundException;
import com.tserashkevich.driverservice.mappers.DriverMapper;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.models.QDriver;
import com.tserashkevich.driverservice.repositories.DriverRepository;
import com.tserashkevich.driverservice.services.CarService;
import com.tserashkevich.driverservice.services.DriverService;
import com.tserashkevich.driverservice.utils.LogList;
import com.tserashkevich.driverservice.utils.QPredicates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final CarService carService;
    private final DriverMapper driverMapper;

    @Transactional
    @Override
    public DriverResponse create(DriverRequest driverRequest) {
        Driver driver = driverMapper.toModel(driverRequest);
        driver.setAvailable(true);
        driverRepository.saveAndFlush(driver);
        List<CarWithoutDriverRequest> carRequests = driverRequest.getCars();
        if (carRequests != null) {
            driver.setCars(carService.create(driver, carRequests));
        }
        log.info(LogList.CREATE_DRIVER, driver.getId());
        return driverMapper.toResponse(driver);
    }

    @Transactional
    @Override
    public DriverResponse update(UUID driverId, DriverUpdateRequest driverUpdateRequest) {
        Driver driver = getOrThrow(driverId);
        driverMapper.updateModel(driver, driverUpdateRequest);
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
    public PageResponse<DriverResponse> findAll(DriverFindAllParams driverFindAllParams) {
        Pageable pageable = PageRequest.of(driverFindAllParams.getPage(), driverFindAllParams.getLimit(), driverFindAllParams.getSort());
        Predicate predicate = QPredicates.builder()
                .add(driverFindAllParams.getGender(), QDriver.driver.gender::eq)
                .add(driverFindAllParams.getBirthDateStart(), QDriver.driver.birthDate::before)
                .add(driverFindAllParams.getBirthDateEnd(), QDriver.driver.birthDate::after)
                .add(driverFindAllParams.getAvaulable(), QDriver.driver.available::eq)
                .build();
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
    public DriverResponse findById(UUID driverId) {
        Driver driver = getOrThrow(driverId);
        log.info(LogList.FIND_DRIVER, driverId);
        return driverMapper.toResponse(driver);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existByPhoneNumber(String phoneNumber) {
        log.info(LogList.EXIST_DRIVER_BY_PHONE_NUMBER, phoneNumber);
        return driverRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Boolean existById(UUID driverId) {
        log.info(LogList.EXIST_DRIVER_BY_ID, driverId);
        return driverRepository.existsById(driverId);
    }

    @Override
    public DriverResponse changeAvailableStatus(UUID driverId, Boolean available) {
        Driver driver = getOrThrow(driverId);
        driver.setAvailable(available);
        driverRepository.save(driver);
        log.info(LogList.CHANGE_DRIVER_STATUS, driverId);
        return driverMapper.toResponse(driver);
    }

    public Driver getOrThrow(UUID driverId) {
        Optional<Driver> optionalCar = driverRepository.findById(driverId);
        return optionalCar.orElseThrow(DriverNotFoundException::new);
    }
}
