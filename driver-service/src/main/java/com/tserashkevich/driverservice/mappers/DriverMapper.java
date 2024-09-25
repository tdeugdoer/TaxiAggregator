package com.tserashkevich.driverservice.mappers;

import com.tserashkevich.driverservice.dtos.DriverRequest;
import com.tserashkevich.driverservice.dtos.DriverResponse;
import com.tserashkevich.driverservice.dtos.DriverUpdateRequest;
import com.tserashkevich.driverservice.models.Driver;
import com.tserashkevich.driverservice.utils.DriverMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CarMapper.class, DriverMapperUtil.class})
public interface DriverMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "available", ignore = true)
    @Mapping(target = "cars", ignore = true)
    Driver toModel(DriverRequest userRequest);

    @Mapping(target = "avgRating", qualifiedByName = "getAvgRating", source = "id")
    DriverResponse toResponse(Driver driver);

    List<DriverResponse> toResponses(List<Driver> drivers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "available", ignore = true)
    @Mapping(target = "cars", ignore = true)
    void updateModel(@MappingTarget Driver driver, DriverUpdateRequest driverUpdateRequest);
}
