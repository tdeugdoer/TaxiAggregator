package com.tserashkevich.driverservice.mappers;

import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.dtos.CarWithoutDriverRequest;
import com.tserashkevich.driverservice.models.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver.id", source = "driver")
    Car toModel(CarRequest carRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    Car toModel(CarWithoutDriverRequest carWithoutDriverRequest);

    List<Car> toModel(List<CarWithoutDriverRequest> carRequests);

    @Mapping(target = "driver", source = "driver.id")
    CarResponse toResponse(Car car);

    List<CarResponse> toResponses(List<Car> cars);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "driver", ignore = true)
    void updateModel(@MappingTarget Car car, CarRequest carRequest);
}
