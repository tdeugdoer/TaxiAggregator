package com.tserashkevich.rideservice.feing;

import com.tserashkevich.rideservice.feing.feignDtos.GeocodeReverseResponse;
import com.tserashkevich.rideservice.feing.feignDtos.RoutingResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Retry(name = "retry-conf")
@CircuitBreaker(name = "circuitbreaker-conf")
@FeignClient(name = "geoapify", configuration = FeignClient.class)
public interface GeoapifyFeignClient {
    @GetMapping("/geocode/reverse")
    GeocodeReverseResponse getCeocodeReverse(@RequestParam String lat,
                                             @RequestParam String lon,
                                             @RequestParam String format,
                                             @RequestParam String apiKey);

    @GetMapping("/routing")
    RoutingResponse getRouting(@RequestParam String waypoints,
                               @RequestParam String mode,
                               @RequestParam String apiKey);
}
