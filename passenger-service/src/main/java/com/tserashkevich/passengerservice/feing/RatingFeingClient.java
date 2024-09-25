package com.tserashkevich.passengerservice.feing;

import com.tserashkevich.passengerservice.configs.feign.FeignConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@Retry(name = "ratings-retry")
@CircuitBreaker(name = "ratings-breaker")
@FeignClient(name = "ratings", configuration = FeignConfig.class)
public interface RatingFeingClient {
    @GetMapping("/avg/{targetId}")
    Double findTargetAvgRating(@PathVariable UUID targetId);
}
