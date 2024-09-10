package com.tserashkevich.driverservice.controllers;

import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.DriverRequest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.PageResponse;
import com.tserashkevich.driverservice.models.enums.Gender;
import com.tserashkevich.driverservice.services.DriverService;
import com.tserashkevich.driverservice.utils.DriverSortList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/drivers")
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse createDriver(@Valid @RequestBody DriverRequest driverRequest) {
        return driverService.create(driverRequest);
    }

    @PutMapping("/{driverId}")
    public DriverResponse updateDriver(@PathVariable UUID driverId, @RequestBody DriverRequest driverRequest) {
        return driverService.update(driverId, driverRequest);
    }

    @DeleteMapping("/{driverId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@PathVariable UUID driverId) {
        driverService.delete(driverId);
    }

    @GetMapping
    public PageResponse<DriverResponse> findAllDrivers(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                        @RequestParam(defaultValue = "20") @Min(1) @Max(50) int limit,
                                                        @RequestParam(defaultValue = "ID_ASC") DriverSortList sort,
                                                        @RequestParam(required = false) Gender gender,
                                                        @RequestParam(required = false) LocalDate birthDateStart,
                                                        @RequestParam(required = false) LocalDate birthDateEnd,
                                                        @RequestParam(required = false) Boolean available) {
        return driverService.findAll(page, limit, sort.getValue(), gender, birthDateStart, birthDateEnd, available);
    }

    @GetMapping("/{driverId}")
    public DriverResponse findDriverById(@PathVariable UUID driverId) {
        return driverService.findOne(driverId);
    }

    @PutMapping("/addCar/{driverId}")
    public DriverResponse addCar(@PathVariable UUID driverId, @RequestBody CarRequest carRequest) {
        return driverService.addCar(driverId, carRequest);
    }

    @PutMapping("/changeStatus/{driverId}")
    public DriverResponse changeAvailableStatus(@PathVariable UUID driverId, @RequestParam Boolean available) {
        return driverService.changeAvailableStatus(driverId, available);
    }
}
