package com.tserashkevich.rideservice.kafka.kafkaDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingCreateEvent {
    private String rideId;
    private UUID sourceId;
    private UUID targetId;
    private Integer rating;
    private String comment;
}
