package com.tserashkevich.driverservice.mappers;

import com.tserashkevich.driverservice.dtos.CarRequest;
import com.tserashkevich.driverservice.dtos.CarResponse;
import com.tserashkevich.driverservice.models.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {
    @Mapping(target = "id", ignore = true)
    Car toModel(CarRequest carRequest);
    CarResponse toResponse(Car car);
    List<CarResponse> toResponses(List<Car> cars);
    @Mapping(target = "id", ignore = true)
    void updateModel(@MappingTarget Car car, CarRequest carRequest);
}
