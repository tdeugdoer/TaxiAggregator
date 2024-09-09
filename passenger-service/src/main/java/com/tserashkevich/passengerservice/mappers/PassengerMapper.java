package com.tserashkevich.passengerservice.mappers;

import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.models.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {
    @Mapping(target = "id", ignore = true)
    Passenger toModel(PassengerRequest userRequest);
    PassengerResponse toResponse(Passenger user);
    List<PassengerResponse> toResponses(List<Passenger> users);
    @Mapping(target = "id", ignore = true)
    void updateModel(@MappingTarget Passenger user, PassengerRequest userRequest);
}
