package com.tserashkevich.rideservice.mappers;

import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.dtos.CreateRideResponse;
import com.tserashkevich.rideservice.dtos.RideResponse;
import com.tserashkevich.rideservice.models.Ride;
import com.tserashkevich.rideservice.models.enums.Status;
import com.tserashkevich.rideservice.utils.GeoMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = GeoMapperUtil.class,
        imports = {LocalDate.class, Status.class}
)
public interface RideMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carId", ignore = true)
    @Mapping(target = "driverId", ignore = true)
    @Mapping(target = "startAddress", qualifiedByName = "mapGeoPointToAddress", source = "startGeoPoint")
    @Mapping(target = "endAddress", qualifiedByName = "mapGeoPointToAddress", source = "endGeoPoint")
    @Mapping(target = "distance", qualifiedByName = "countDistance", source = "createRideRequest")
    @Mapping(target = "time.startTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(Status.CREATED)")
    Ride toModel(CreateRideRequest createRideRequest);

    @Mapping(target = "startTime", source = "ride.time.startTime")
    CreateRideResponse toCreateRideResponse(Ride ride);

    RideResponse toRideResponse(Ride ride);

    List<RideResponse> toRideResponses(List<Ride> ride);
}