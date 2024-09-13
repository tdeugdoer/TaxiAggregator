package com.tserashkevich.rideservice.models;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Time {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
