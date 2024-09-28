package com.tserashkevich.rideservice.feing;

import com.tserashkevich.rideservice.configs.feign.FeignConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Retry(name = "retry-conf")
@CircuitBreaker(name = "circuitbreaker-conf")
@FeignClient(name = "driver", configuration = FeignConfig.class)
public interface DriverFeignClient {
    @GetMapping("/exist/{driverId}")
    Boolean getExistDriver(@PathVariable UUID driverId);
}
