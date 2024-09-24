package com.tserashkevich.rideservice.feing;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Retry(name = "retry-conf")
@CircuitBreaker(name = "circuitbreaker-conf")
@FeignClient(name = "car", configuration = FeignClient.class)
public interface CarFeignClient {
    @GetMapping("/exist/{carId}")
    Boolean getExistCar(@PathVariable Long carId);
}
