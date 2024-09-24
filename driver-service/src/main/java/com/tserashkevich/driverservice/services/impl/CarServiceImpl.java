package com.tserashkevich.driverservice.services.impl;

import com.querydsl.core.types.Predicate;
import com.tserashkevich.driverservice.dtos.*;
import com.tserashkevich.driverservice.exceptions.CarNotFoundException;
import com.tserashkevich.driverservice.mappers.CarMapper;
import com.tserashkevich.driverservice.models.Car;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.models.QCar;
import com.tserashkevich.driverservice.repositories.CarRepository;
import com.tserashkevich.driverservice.services.CarService;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public CarResponse create(CarRequest carRequest) {
        Car car = carMapper.toModel(carRequest);
        carRepository.save(car);
        log.info(LogList.CREATE_CAR, car.getId());
        return carMapper.toResponse(car);
    }

    @Override
    public List<Car> create(Driver driver, List<CarWithoutDriverRequest> carRequests) {
        List<Car> cars = carMapper.toModel(carRequests);
        cars.forEach(car -> car.setDriver(driver));
        carRepository.saveAll(cars);
        log.info(LogList.CREATE_CARS, cars.stream()
                .map(Car::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
        return cars;
    }

    @Override
    public CarResponse update(Long carId, CarRequest carRequest) {
        Car car = getOrThrow(carId);
        carMapper.updateModel(car, carRequest);
        carRepository.save(car);
        log.info(LogList.EDIT_CAR, carId);
        return carMapper.toResponse(car);
    }

    @Override
    public void delete(Long carId) {
        carRepository.delete(getOrThrow(carId));
        log.info(LogList.DELETE_CAR, carId);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CarResponse> findAll(CarFindAllParams carFindAllParams) {
        Pageable pageable = PageRequest.of(carFindAllParams.getPage(), carFindAllParams.getLimit(), carFindAllParams.getSort());
        Predicate predicate = QPredicates.builder()
                .add(carFindAllParams.getNumber(), QCar.car.number::like)
                .add(carFindAllParams.getBrand(), QCar.car.brand::like)
                .add(carFindAllParams.getModel(), QCar.car.model::like)
                .add(carFindAllParams.getColor(), QCar.car.color::eq)
                .build();
        Page<Car> carPage = carRepository.findAll(predicate, pageable);
        List<CarResponse> carResponses = carMapper.toResponses(carPage.getContent());
        log.info(LogList.FIND_ALL_CARS);
        return PageResponse.<CarResponse>builder()
                .objectList(carResponses)
                .totalElements(carPage.getTotalElements())
                .totalPages(carPage.getTotalPages())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public CarResponse findById(Long carId) {
        Car car = getOrThrow(carId);
        log.info(LogList.FIND_CAR, carId);
        return carMapper.toResponse(car);
    }

    @Override
    public Boolean existById(Long carId) {
        return carRepository.existsById(carId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existByCarNumber(String carNumber) {
        return carRepository.existsByNumber(carNumber);
    }

    public Car getOrThrow(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.orElseThrow(CarNotFoundException::new);
    }
}
