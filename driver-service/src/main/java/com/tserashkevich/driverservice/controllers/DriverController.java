package com.tserashkevich.driverservice.controllers;

import com.tserashkevich.driverservice.dtos.*;
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
    public DriverResponse updateDriver(@PathVariable UUID driverId, @Valid @RequestBody DriverUpdateRequest driverUpdateRequest) {
        return driverService.update(driverId, driverUpdateRequest);
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
        DriverFindAllParams driverFindAllParams = DriverFindAllParams.builder()
                .page(page)
                .limit(limit)
                .sort(sort.getValue())
                .gender(gender)
                .birthDateStart(birthDateStart)
                .birthDateEnd(birthDateEnd)
                .avaulable(available)
                .build();
        return driverService.findAll(driverFindAllParams);
    }

    @GetMapping("/{driverId}")
    public DriverResponse findDriverById(@PathVariable UUID driverId) {
        return driverService.findById(driverId);
    }

    @PutMapping("/changeStatus/{driverId}")
    public DriverResponse changeAvailableStatus(@PathVariable UUID driverId) {
        return driverService.changeAvailableStatus(driverId);
    }

    @GetMapping("/exist/{driverId}")
    public Boolean existDriver(@PathVariable UUID driverId) {
        return driverService.existById(driverId);
    }
}
