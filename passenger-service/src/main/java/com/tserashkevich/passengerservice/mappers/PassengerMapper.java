package com.tserashkevich.passengerservice.mappers;

import com.tserashkevich.passengerservice.dtos.PassengerRequest;
import com.tserashkevich.passengerservice.dtos.PassengerResponse;
import com.tserashkevich.passengerservice.models.Passenger;
import com.tserashkevich.passengerservice.utils.PassengerMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = PassengerMapperUtil.class
)
public interface PassengerMapper {
    @Mapping(target = "id", ignore = true)
    Passenger toModel(PassengerRequest passengerRequest);

    @Mapping(target = "avgRating", qualifiedByName = "getAvgRating", source = "id")
    PassengerResponse toResponse(Passenger passenger);

    List<PassengerResponse> toResponses(List<Passenger> passengers);

    @Mapping(target = "id", ignore = true)
    void updateModel(@MappingTarget Passenger user, PassengerRequest userRequest);
}
