package com.tserashkevich.rideservice.feing;

import com.tserashkevich.rideservice.feing.feignDtos.PassengerExistResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Retry(name = "retry-conf")
@CircuitBreaker(name = "circuitbreaker-conf")
@FeignClient(name = "passenger", configuration = FeignClient.class)
public interface PassengerFeignClient {
    @GetMapping("/exist/{passengerId}")
    PassengerExistResponse getExistPassenger(@PathVariable UUID passengerId);
}
