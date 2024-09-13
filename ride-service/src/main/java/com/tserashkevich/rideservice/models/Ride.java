package com.tserashkevich.rideservice.models;

import com.tserashkevich.rideservice.models.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(value = "rides")
public class Ride {
    @Id
    private String id;
    private UUID driverId;
    private UUID passengerId;
    private Long carId;
    private Address startAddress;
    private Address endAddress;
    private Integer distance;
    private Time time;
    private Status status;
}