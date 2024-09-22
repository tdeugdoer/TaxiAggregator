package com.tserashkevich.rideservice.utils;

import com.tserashkevich.rideservice.configs.GeoapifyProperties;
import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.feing.GeoapifyFeignClient;
import com.tserashkevich.rideservice.feing.feignDtos.GeocodeReverseResponse;
import com.tserashkevich.rideservice.feing.feignDtos.RoutingResponse;
import com.tserashkevich.rideservice.models.Address;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Named("GeoMapperUtil")
@RequiredArgsConstructor
@Component
public class GeoMapperUtil {
    private final GeoapifyFeignClient geoapifyFeignClient;
    private final GeoapifyProperties geoapifyProperties;

    @Named("mapGeoPointToAddress")
    public Address mapGeoPointToAddress(String geoPoint) {
        List<String> coordinates = List.of(geoPoint.split(","));
        GeocodeReverseResponse geocodeReverseResponse = geoapifyFeignClient.getCeocodeReverse(coordinates.get(0),
                coordinates.get(1),
                "json",
                geoapifyProperties.getApiKey());
        return Address.builder()
                .geoPoint(geocodeReverseResponse.getResults().get(0).getLat() + "|" + geocodeReverseResponse.getResults().get(0).getLon())
                .name(geocodeReverseResponse.getResults().get(0).getFormatted())
                .build();
    }

    @Named("countDistance")
    public Integer countDistance(CreateRideRequest createRideRequest) {
        RoutingResponse routingResponse = geoapifyFeignClient.getRouting(
                createRideRequest.getStartGeoPoint() + "|" + createRideRequest.getEndGeoPoint(),
                "drive",
                geoapifyProperties.getApiKey()
        );
        return routingResponse.getFeatures().get(0).getProperties().getDistance();
    }
}
