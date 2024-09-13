package com.tserashkevich.rideservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tserashkevich.rideservice.configs.GeoapifyProperties;
import com.tserashkevich.rideservice.dtos.CreateRideRequest;
import com.tserashkevich.rideservice.exceptions.GeoapifyException;
import com.tserashkevich.rideservice.exceptions.JsonReadException;
import com.tserashkevich.rideservice.models.Address;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Named("GeoMapperUtil")
@RequiredArgsConstructor
@Component
public class GeoMapperUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplateBuilder restTemplateBuilder;
    private final GeoapifyProperties geoapifyProperties;

    @Named("mapGeoPointToAddress")
    public Address mapGeoPointToAddress(String geoPoint) {
        try {
            List<String> coordinates = List.of(geoPoint.split(","));
            String url = String.format("https://api.geoapify.com/v1/geocode/reverse?lat=%s&lon=%s&apiKey=%s",
                    coordinates.get(0),
                    coordinates.get(1),
                    geoapifyProperties.getApiKey());
            ResponseEntity<String> response = restTemplateBuilder.build()
                    .exchange(url,
                            HttpMethod.GET,
                            null,
                            String.class);
            checkResponse(response);
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String address = rootNode.path("features").path(0).path("properties").path("formatted").asText();
            return Address.builder()
                    .geoPoint(coordinates.get(0) + "|" + coordinates.get(1))
                    .name(address)
                    .build();

        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonReadException();
        } catch (RestClientException e) {
            throw new GeoapifyException(e.getMessage());
        }
    }

    @Named("countDistance")
    public Integer countDistance(CreateRideRequest createRideRequest) {
        try {
            String url = String.format("https://api.geoapify.com/v1/routing?waypoints=%s&mode=%s&apiKey=%s",
                    createRideRequest.getStartGeoPoint() + "|" + createRideRequest.getEndGeoPoint(),
                    "drive",
                    geoapifyProperties.getApiKey());
            ResponseEntity<String> response = restTemplateBuilder.build()
                    .exchange(url,
                            HttpMethod.GET,
                            null,
                            String.class);
            checkResponse(response);
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return rootNode.path("features").path(0).path("properties").path("distance").intValue();
        } catch (JsonProcessingException jsonProcessingException) {
            throw new JsonReadException();
        }
    }

    private static void checkResponse(ResponseEntity<String> response) {
        if (response.getStatusCode().isError()) {
            throw new GeoapifyException();
        }
    }
}
