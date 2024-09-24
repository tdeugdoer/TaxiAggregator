package com.tserashkevich.driverservice.controllers;

import com.tserashkevich.driverservice.dtos.CarFindAllParams;
import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.models.enums.Color;
import com.tserashkevich.driverservice.services.CarService;
import com.tserashkevich.driverservice.utils.CarSortList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse createCar(@Valid @RequestBody CarRequest carRequest) {
        return carService.create(carRequest);
    }

    @PutMapping("/{carId}")
    public CarResponse updateCar(@PathVariable Long carId, @Valid @RequestBody CarRequest carRequest) {
        return carService.update(carId, carRequest);
    }

    @DeleteMapping("/{carId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Long carId) {
        carService.delete(carId);
    }

    @GetMapping
    public PageResponse<CarResponse> findAllCars(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                        @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                        @RequestParam(defaultValue = "ID_ASC") CarSortList sort,
                                                        @RequestParam(required = false) String number,
                                                        @RequestParam(required = false) String brand,
                                                        @RequestParam(required = false) String model,
                                                        @RequestParam(required = false) Color color) {
        CarFindAllParams carFindAllParams = CarFindAllParams.builder()
                .page(page)
                .limit(limit)
                .sort(sort.getValue())
                .number(number)
                .brand(brand)
                .model(model)
                .color(color)
                .build();
        return carService.findAll(carFindAllParams);
    }

    @GetMapping("/{carId}")
    public CarResponse findCarById(@PathVariable Long carId) {
        return carService.findById(carId);
    }

    @GetMapping("/exist/{carId}")
    public Boolean existCar(@PathVariable Long carId) {
        return carService.existById(carId);
    }
}
